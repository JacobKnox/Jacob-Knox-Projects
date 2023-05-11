# bottledwater.py
"""Perform principal component analysis on the bottled water dataset then use the reduced data to train a clustering model and a support vector machine on it."""

from argparse import ArgumentParser
from os import path
import numpy as np
from sklearn.cluster import KMeans
from sklearn import svm as SVM
import matplotlib.pyplot as plt

ROOT = path.dirname(__file__)

parser = ArgumentParser(description = "Perform PCA and clustering on the bottled water dataset and train an SVM with it.")
parser.add_argument('-x', '--training_data',
                    help = f'Path to the training data file, defaults to {ROOT}/bottledwater/bottledwater.csv',
                    default = path.join(ROOT, 'bottledwater/bottledwater.csv'))
parser.add_argument('-y', '--training_labels',
                    help = f'Path to the training labels file, defaults to {ROOT}/bottledwater/bottledwaterlabels.csv',
                    default = path.join(ROOT, 'bottledwater/bottledwaterlabels.csv'))
parser.add_argument('-xt', '--testing_data',
                    help = f'Path to the testing data file, defaults to {ROOT}/bottledwater/testsamples.csv',
                    default = path.join(ROOT, 'bottledwater/testsamples.csv'))
parser.add_argument('-t', '--threshold',
                    help = 'Number between 0 and 1, inclusive, representing the percent of variance to keep during PCA, defaults to 0.9',
                    default = 0.9,
                    type = float)


def main(args):
    # Get the file paths from the arguments and parse the data
    xtrain, features, ytrain, xtest = parse_data(args.training_data, args.training_labels, args.testing_data)
    # Gets the unique labels of the data
    labels = np.unique(ytrain)
    # Perform PCA on the data and get the reduced information
    reduced_xtrain, reduced_features, reduced_xtest = pca(xtrain, features, args.threshold, xtest)
    # Perform clustering and get the resulting labels and predictions
    clustering_results = clustering((reduced_xtrain, ytrain, reduced_features))
    # Model a support vector machine using the data and clustering results
    svm((reduced_xtrain, reduced_xtest), clustering_results)


def parse_data(training_data_path: str, training_labels_path: str, testing_data_path: str) -> tuple[np.ndarray, np.ndarray, np.ndarray, np.ndarray]:
    """Takes in the file paths for the data and parses them into numpy arrays

    Args:
        training_data_path (str): Path of the training data file
        training_labels_path (str): Path of the training labels file
        testing_data_path (str): Path of the testing data file

    Returns:
        np.ndarray: Array of floats representing the training data
        np.ndarray: Array of strings representing the features of the data
        np.ndarray: Array of strings representing the labels of the training data
        np.ndarray: Array of floats representing the testing data
    """
    
    print("\n===== PARSING DATA =====\n")
    print("Loading training data...")
    xtrain = np.loadtxt(training_data_path, dtype = str, delimiter = ",")
    # Take the first line as strings, since they're the features
    print("Extracting features from data...")
    features = xtrain[0]
    # Convert the rest of the lines to floats
    xtrain = xtrain[1:].astype(float)
    print("Loading training labels...")
    ytrain = np.loadtxt(training_labels_path, dtype = str)
    print("Loading testing data...")
    xtest = np.loadtxt(testing_data_path, dtype = str, delimiter = ",")
    # Convert every line but the first one to floats and ignore the first line since we already have it in features
    xtest = xtest[1:].astype(float)
    print("\n===== PARSING COMPLETE =====\n")
    return xtrain, features, ytrain, xtest


def pca(xtrain: np.ndarray, features: np.ndarray, threshold: float, xtest: np.ndarray) -> tuple[np.ndarray, np.ndarray, np.ndarray]:
    """Takes in a data array, an array of its features, and a threshold of variability to keep then performs PCA on the data array

    Args:
        xtrain (np.ndarray): Array of data samples
        features (np.ndarray): Array of features in the data
        threshold (float): Percentage of variability to keep (represented as a number between 0 and 1)
        xtest (np.ndarray): Array of test samples

    Returns:
        np.ndarray: Array of floats representing the reduced data
        np.ndarray: Array of strings representing the reduced features of the data
        np.ndarray: Array of floats representing the reduced test samples
    """
    
    print("\n===== PERFORMING PCA ON DATA =====\n")
    # Compute the average face
    print("Computing mean of the training data...")
    mu = np.mean(xtrain, axis = 0)
    # Compute the difference between images and the mean
    print("Computing A matrix, difference between training data and mean...")
    A = xtrain - mu
    # Make the scatter matrix using the "trick" for images (AtA instead of AAt)
    print("Computing scatter matrix...")
    S = np.matmul(A.T, A)
    # Compute the eigenvalues and eigenvectors (PCs)
    print("Computing eigenvalues and eigenvectors...")
    eigvalues, eigvectors = np.linalg.eig(S)
    # Sort them in descending order based on the eigenvalues
    print("Sorting eigenvalues, eigenvectors, and data...")
    ind = np.argsort(eigvalues)[::-1]
    eigvalues = eigvalues[ind]
    eigvectors = eigvectors[:, ind]
    features = features[ind]
    xtrain = xtrain[:, ind]
    xtest = xtest[:, ind]
    # Loop over the eigenvalues
    print(f"Choosing which eigenvalues to keep using threshold {threshold}...")
    for i in range(len(eigvalues)):
        # If the percentage of eigenvalues up to i is greater than the threshold, then stop
        if(sum(eigvalues[:i]) / sum(eigvalues) >= threshold):
            print(f"Found eigenvectors that explain >= {threshold * 100}% of the variance: {eigvalues[:i]} ({i} eigenvalues).")
            print("\n===== PCA COMPLETE =====\n")
            return xtrain[:, :i], features[:i], xtest[:, :i]
        print(f"{features[i]}: {eigvalues[i]} (eigenvalue) and {eigvectors[i]} (eigenvector)")
        
#################
# DOCUMENT MORE #
#################

# Fix this to make it more generalizable and useful for other cases
def show_data(x, y, labels, title, mu = None, block = False):
    '''Display sample data (x) with corresponding labels (y) and cluster means (mu).'''
    plt.figure(1)
    plt.clf()
    plt.xlabel(labels[0])
    plt.ylabel(labels[1])
    plt.title(title)
    s1 = plt.scatter(x[:, 0], x[:, 1], s=6, c=y)
    if mu is not None:
        s2 = plt.plot(mu[:, 0], mu[:, 1], color='red', ms=8, marker='o', ls='')
    plt.ion()
    plt.show(block=block)
    

def clustering(data: tuple[np.ndarray, np.ndarray, np.ndarray]) -> tuple[np.ndarray, np.ndarray]:
    """Takes in the data and performs KMeans clustering on it. Returns resulting cluster labels and predictions.

    Args:
        data (tuple[np.ndarray, np.ndarray, np.ndarray]): A "zipped" form of the reduced data from PCA

    Returns:
        np.ndarray: Array of the labels resulting from the clustering
        np.ndarray: Array of the predictions from the clustering
    """
    
    # Extract the data from the tuple
    reduced_xtrain, ytrain, reduced_features = data
    print("\n=== PERFORMING CLUSTERING ===\n")
    # Get a rough estimate of the number of clusters based on the number of unique labels
    num_clusters = len(np.unique(ytrain))
    print("Creating and training k-means clustering model...")
    # Create a KMeans model
    clusters = KMeans(n_clusters=num_clusters)
    # Fit the KMeans model on the reduced data
    trained_clusters = clusters.fit(reduced_xtrain, ytrain)
    print("Predicting with the training data...")
    # Test the model on the reduced training data
    clustering_predictions = trained_clusters.predict(reduced_xtrain)
    # Get the cluster centers
    mu = trained_clusters.cluster_centers_
    show_data(reduced_xtrain, clustering_predictions, mu, reduced_features, "KMeans Clustering", True)
    clustering_labels = []
    print("Computing accuracy...")
    for i in range(0, num_clusters):
        # Get all the true labels for the data where the prediction is i (ytrain[np.where(clustering_predictions == i))
        # Find the unique labels and the counts of each
        unique, counts = np.unique(ytrain[np.where(clustering_predictions == i)], return_counts = True) # tmw you write a one liner and don't exactly remember what it does
        # Add the label with the most predictions as the ith cluster label
        clustering_labels.append(unique[np.argmax(counts)])
    # Compute the accuracy: find the labels associated with the predictions ([clustering_labels[i] for i in clustering_predictions]), match them to the true labels, sum it up, and divide by the length
    accuracy = sum([clustering_labels[i] for i in clustering_predictions] == ytrain) / len(ytrain)
    print(f'Accuracy of clusters: {accuracy}')
    print("\n=== CLUSTERING COMPLETE ===\n")
    return clustering_labels, clustering_predictions


def svm(data: tuple[np.ndarray, np.ndarray], clustering_results: tuple[np.ndarray, np.ndarray]):
    """Trains a support vector machine using the training data and clustering labels then makes predictions on the testing data.

    Args:
        data (tuple[np.ndarray, np.ndarray]): A "zipped" tuple of the data containina the reduced training and testing data
        clustering_results (tuple[np.ndarray, np.ndarray]): A "zipped" tuple of the clustering information returned by the clustering function
    """
    
    # Extract the reduced data from the tuple
    reduced_xtrain, reduced_xtest = data
    # Extract the clustering information from the tuple
    clustering_labels, clustering_predictions = clustering_results
    print("\n=== TRAINING SVM ===\n")
    print("Creating and training SVM...")
    # Create an SVC support vector machine model
    svm = SVM.SVC(kernel='linear')
    # Train the model on the reduced training data with the clustering predictions as labels
    svm.fit(reduced_xtrain, clustering_predictions)
    print("Predicting for testing data and labelling...")
    # Use the model to predict and label the test data
    svm_predictions = svm.predict(reduced_xtest)
    labeled_predictions = [clustering_labels[i] for i in svm_predictions]
    print(f'Predictions of SVM: {labeled_predictions}')
    print("\n=== SVM COMPLETE ===\n")


if __name__ == '__main__':
    main(parser.parse_args())