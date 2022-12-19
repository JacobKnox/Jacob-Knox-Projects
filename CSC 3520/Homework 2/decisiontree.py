# decisiontree.py
"""Predict Parkinson's disease based on dysphonia measurements using a decision tree."""

import matplotlib.pyplot as plt
import numpy as np
import os
import pdb
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.metrics import confusion_matrix

ROOT = os.path.dirname(os.path.abspath(__file__))  # root directory of this code


def main():
    # Relevant files
    datafile = os.path.expanduser(os.path.join(ROOT, "data", "trainingdata.txt"))
    labelfile = os.path.expanduser(os.path.join(ROOT, "data", "traininglabels.txt"))
    attributesfile = os.path.expanduser(os.path.join(ROOT, "data", "attributes.txt"))
    testdatafile = os.path.expanduser(os.path.join(ROOT, "data", "testingdata.txt"))
    testlabelfile = os.path.expanduser(os.path.join(ROOT, "data", "testinglabels.txt"))

    # Load data from relevant files
    xtrain = np.loadtxt(datafile, dtype = float, delimiter=",", ndmin=2)
    ytrain = np.loadtxt(labelfile, dtype = int)
    attributes = np.loadtxt(attributesfile, dtype = str)
    xtest = np.loadtxt(testdatafile, dtype = float, delimiter=",", ndmin=2)
    ytest = np.loadtxt(testlabelfile, dtype = int)

    # Train a decision tree via information gain on the training data
    clf = DecisionTreeClassifier()
    clf = clf.fit(xtrain, ytrain)

    # Test the decision tree
    test = clf.predict(xtest)
    train = clf.predict(xtrain)

    # Show the confusion matrix for test data
    cm = confusion_matrix(ytest, test)
    print("Confusion matrix:")
    print(cm)

    # Compare training and test accuracy
    testerror = np.abs(ytest - test)
    trainerror = np.abs(ytrain - train)
    print("Training Accuracy: " + str((len(trainerror) - sum(trainerror)) / len(trainerror)))
    print("Testing Accuracy: " + str((len(testerror) - sum(testerror)) / len(testerror)))

    # Visualize the tree using matplotlib and plot_tree
    fig = plt.figure(figsize=(50,50))
    plot_tree(clf, feature_names = attributes, filled = True, fontsize = 28, class_names = ["0", "1"], rounded = True)
    fig.show()
    fig.savefig("decistion_tree.png")


if __name__ == '__main__':
    main()
