# naivebayes.py
"""Perform document classification using a Naive Bayes model."""

import argparse
import os
import numpy as np
from sklearn.metrics import confusion_matrix
import matplotlib.pyplot as plt

ROOT = '~\Documents\Fall 2022 Courses\CSC 3520 - Machine Learning\Code\csc3520_hw1_f22\data'  # change to path where data is stored

parser = argparse.ArgumentParser(description="Use a Naive Bayes model to classify text documents.")
parser.add_argument('-x', '--training_data',
                    help='path to training data file, defaults to ROOT/trainingdata.txt',
                    default=os.path.join(ROOT, 'trainingdata.txt'))
parser.add_argument('-y', '--training_labels',
                    help='path to training labels file, defaults to ROOT/traininglabels.txt',
                    default=os.path.join(ROOT, 'traininglabels.txt'))
parser.add_argument('-xt', '--testing_data',
                    help='path to testing data file, defaults to ROOT/testingdata.txt',
                    default=os.path.join(ROOT, 'testingdata.txt'))
parser.add_argument('-yt', '--testing_labels',
                    help='path to testing labels file, defaults to ROOT/testinglabels.txt',
                    default=os.path.join(ROOT, 'testinglabels.txt'))
parser.add_argument('-n', '--newsgroups',
                    help='path to newsgroups file, defaults to ROOT/newsgroups.txt',
                    default=os.path.join(ROOT, 'newsgroups.txt'))
parser.add_argument('-v', '--vocabulary',
                    help='path to vocabulary file, defaults to ROOT/vocabulary.txt',
                    default=os.path.join(ROOT, 'vocabulary.txt'))

def get_priors(labels):
    uniques, counts = np.unique(labels, return_counts=True)
    return np.add(np.zeros(len(uniques)), counts / sum(counts))

def get_class_conditionals(num_newsgroups, num_words, xtrain, ytrain):
    alpha = 0.055
    class_conditionals = np.zeros((num_words, num_newsgroups)) + alpha
    for row in xtrain:
        class_conditionals[row[1]][ytrain[row[0]]] += row[2]
    class_conditionals = np.multiply(class_conditionals, 1 / np.sum(class_conditionals, axis = 0))
    return class_conditionals

def main(args):
    print("Document Classification using Naïve Bayes Classifiers")
    print("=======================")
    print("PRE-PROCESSING")
    print("=======================")

    # Parse input arguments
    training_data_path = os.path.expanduser(args.training_data)
    training_labels_path = os.path.expanduser(args.training_labels)
    testing_data_path = os.path.expanduser(args.testing_data)
    testing_labels_path = os.path.expanduser(args.testing_labels)
    newsgroups_path = os.path.expanduser(args.newsgroups)
    vocabulary_path = os.path.expanduser(args.vocabulary)

    # Load data from relevant files
    # ***MODIFY CODE HERE*** - DONE
    print("Loading training data...")
    xtrain = np.loadtxt(training_data_path, dtype = int, delimiter = " ")
    print("Loading training labels...")
    ytrain = np.loadtxt(training_labels_path, dtype = int)
    print("Loading testing data...")
    xtest = np.loadtxt(testing_data_path, dtype = int, delimiter = " ")
    print("Loading testing labels...")
    ytest = np.loadtxt(testing_labels_path, dtype = int)
    print("Loading newsgroups...")
    newsgroups = np.loadtxt(newsgroups_path, dtype = str)
    print("Loading vocabulary...")
    vocabulary = np.loadtxt(vocabulary_path, dtype = str)

    # Change 1-indexing to 0-indexing for labels, docID, wordID
    # ***MODIFY CODE HERE*** - DONE
    ytrain -= 1
    xtrain = np.add(xtrain, [-1, -1, 0])
    ytest -= 1
    xtest = np.add(xtest, [-1, -1, 0])

    # Extract useful parameters
    num_testing_documents = len(np.unique(np.moveaxis(xtest, 0, 1)[0]))
    num_words = len(vocabulary)
    num_newsgroups = len(newsgroups)

    print("\n=======================")
    print("TRAINING")
    print("=======================")

    # Estimate the prior probabilities
    print("Estimating prior probabilities via MLE...")
    # ***MODIFY CODE HERE*** - DONE
    priors = get_priors(ytrain)

    # Estimate the class conditional probabilities
    print("Estimating class conditional probabilities via MAP...")
    # ***MODIFY CODE HERE*** - DONE
    class_conditionals = get_class_conditionals(num_newsgroups, num_words, xtrain, ytrain)
    print("\n=======================")
    print("TESTING")
    print("=======================")

    # Test the Naive Bayes classifier
    print("Applying natural log to prevent underflow...")
    # ***MODIFY CODE HERE*** - DONE
    log_priors = np.log(priors, out=np.zeros_like(priors), where=(priors!=0))
    log_class_conditionals = np.log(class_conditionals, out=np.zeros_like(class_conditionals), where=(class_conditionals!=0))

    print("Counting words in each document...")
    # ***MODIFY CODE HERE*** - DONE
    counts = np.zeros((num_testing_documents, num_words))
    for row in xtest:
        counts[row[0]][row[1]] += row[2]

    print("Computing posterior probabilities...")
    # ***MODIFY CODE HERE***
    log_posterior = np.add(log_priors, np.matmul(counts, log_class_conditionals))

    print("Assigning predictions via argmax...")
    # ***MODIFY CODE HERE***
    pred = np.argmax(log_posterior, axis = 1)

    print("\n=======================")
    print("PERFORMANCE METRICS")
    print("=======================")

    # Compute performance metrics
    # ***MODIFY CODE HERE***
    accuracy = sum(pred == ytest) / len(ytest)
    print("Accuracy: " + str(sum(pred == ytest)) + "/" + str(len(ytest)) + "(" + str.format("{:.2f}", accuracy * 100) + "%)")
    cm = confusion_matrix(ytest, pred)
    print("Confusion matrix:")
    print(cm)

    alphas = [0.055, 0.05, 0.06, 0.0525, 0.0575, 0.04, 0.075, 0.025, 0.204, 0.203, 0.2025, 0.201, 0.2, 0.205, 0.19, 0.0175, 0.21, 0.1, 0.015, 0.0125, 0.01, 0.3, 0.4, 0.5, 0.001, 0.0015, 0.6, 0.0001, 0.00001634307, 0.00001, 1]
    accuracies = [0.8079946702, 0.8075949367, 0.8075949367, 0.8075949367, 0.8074616922, 0.8071952032, 0.8065289807, 0.8062624917, 0.8057295137, 0.8057295137, 0.8057295137, 0.8057295137, 0.8055962692, 0.8054630247, 0.8054630247, 0.8054630247, 0.8051965356, 0.8046635576, 0.8046635576, 0.8038640906, 0.8037308461, 0.8035976016, 0.801065956, 0.798667555, 0.7970686209, 0.7966688874, 0.7941372418, 0.7889407062, 0.7852098601, 0.7838774151, 0.7810792805]
    _, ax = plt.subplots()
    ax.scatter(alphas, accuracies)
    ax.semilogx()
    ax.set_xlabel("Alpha")
    ax.set_ylabel("Accuracy")
    plt.show()
    # pdb.set_trace()  # uncomment for debugging, if needed


if __name__ == '__main__':
    main(parser.parse_args())