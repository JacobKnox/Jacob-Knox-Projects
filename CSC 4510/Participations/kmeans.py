# kmeans.py
# Demo of k-means clustering algorithm on synthetic 2D data.

import argparse
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cm as colormaps
from sklearn.datasets import make_blobs
from scipy.spatial.distance import cdist
import pdb

parser = argparse.ArgumentParser(description='Run k-means clustering on synthetic 2D data.')
parser.add_argument('-k', metavar='clusters', type=int, help='number of clusters to generate (default=5)', default=5)
parser.add_argument('-s', '--seed', metavar='m', type=int, help='random seed for repeatability (default=None)', default=None)
parser.add_argument('-i', '--iter', metavar='n', type=int, help='maximum number of iterations (default=100)', default=100)
parser.add_argument('-b', '--blobs', metavar='file', type=str, help='file containing blob parameters for synthetic data (default=blobs.txt)', default='blobs.txt')
parser.add_argument('-w', '--wait', metavar='sec', type=float, help='number of seconds to wait between display updates (default=1)', default=1)


def main(args):
    print("============================")
    print("K-MEANS CLUSTERING")
    print("============================")

    # Setup problem
    np.random.seed(args.seed)  # initialize random number generator (for repeatability)
    x = generate_data(args.blobs)  # make synthetic 2D data
    mu = np.random.uniform(-8, 8, size=(args.k, 2))  # initialize k-means
    s1, s2 = show_data(x, mu)  # outputs are handles to scatter plots

    # Run k-means iteratively
    old_labels = []
    for i in range(args.iter):
        print("Iteration {} of {}".format(i + 1, args.iter))
        # EDIT CODE HERE
        labels = update_labels(x, mu, s1)
        mu = update_means(x, mu, labels, s2)

        update_display(wait=args.wait)
        
        if np.all(old_labels == labels):
            print("Stopping early, because labels didn't change.")
            break
        
        old_labels = labels

    # Wait for user to click on figure
    print('\nClick on figure to exit...')
    plt.waitforbuttonpress()


def generate_data(filename):
    '''Generate synthetic 2D Gaussian data from file with blob parameters.'''
    # Read blob parameters from file: expected format is (number of samples, mean [x, y], standard deviation)
    with open(filename) as file:
        params = [[float(i) for i in line.rstrip().split(',')] for line in file]

    # Make and concatenate blobs
    x = np.empty((0, 2))
    for p in params:
        x0, _ = make_blobs(n_samples=int(p[0]), centers=[[p[1], p[2]]], cluster_std=p[3])
        x = np.concatenate((x, x0))
    
    # Return synthetic data
    return x


def show_data(x, mu):
    '''Setup graphics for displaying sample data (x) and cluster means (mu).'''
    plt.figure(1)
    plt.clf()
    s1 = plt.scatter(x[:, 0], x[:, 1], s=6)
    ax = plt.gca()
    ax.set_xlim(-10, 10)
    ax.set_ylim(-10, 10)
    ax.axis('off')
    s2, = plt.plot(mu[:, 0], mu[:, 1], color='black', ms=8, marker='o', ls='')
    plt.ion()
    plt.show()

    return s1, s2  # return handles to scatter plots so they can be updated


def update_labels(x, mu, s1):
    '''Assign labels to each data sample in x based on the cluster means mu.'''
    # EDIT CODE HERE
    labels = np.argmin(cdist(x, mu, 'euclidean'), axis=1)
    
    # Color the data points on the plot
    cmap = colormaps.Pastel1(list(range(len(mu))))
    s1.set_color(cmap[labels - 1, :])

    return labels


def update_means(x, mu, labels, s2):
    '''Compute the new means for the data x based on the labels.'''
    # EDIT CODE HERE
    mu = np.asarray([(0 if len(x[labels==k, :]) == 0 else sum(x[labels==k, :])/len(x[labels==k, :])) for k in range(0, len(mu))])
    
    # Move the mean data points on the plot
    s2.set_data(mu[:, 0], mu[:, 1])
    
    return mu


def update_display(wait=0.5):
    '''Utility function to update the matplotlib plot and wait a specified amount of time.'''
    plt.draw()
    plt.pause(wait)


if __name__ == '__main__':
    main(parser.parse_args())
