# kmeans_sklearn.py
# Demo of k-means clustering algorithm on synthetic 2D data (using built-in functions!).

import argparse
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cm as colormaps
from sklearn.datasets import make_blobs
from sklearn.cluster import KMeans
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

    # Run k-means
    # EDIT CODE HERE
    model = KMeans(n_clusters=args.k, verbose=0)
    y = model.fit_predict(x)
    mu = model.cluster_centers_

    # Show results
    show_data(x, y, mu)

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


def show_data(x, y, mu):
    '''Display sample data (x) with corresponding labels (y) and cluster means (mu).'''
    plt.figure(1)
    plt.clf()
    s1 = plt.scatter(x[:, 0], x[:, 1], s=6, c=y)
    ax = plt.gca()
    ax.set_xlim(-10, 10)
    ax.set_ylim(-10, 10)
    ax.axis('off')
    s2, = plt.plot(mu[:, 0], mu[:, 1], color='red', ms=8, marker='o', ls='')
    plt.ion()
    plt.show()


if __name__ == '__main__':
    main(parser.parse_args())
