import argparse
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.cm as colormaps
from sklearn.datasets import make_blobs
from sklearn.cluster import AgglomerativeClustering
from scipy.cluster import hierarchy
import pdb

parser = argparse.ArgumentParser(description='Run k-means clustering on synthetic 2D data.')
parser.add_argument('-k', metavar='clusters', type=int, help='number of clusters to generate (default=5)', default=5)
parser.add_argument('-s', '--seed', metavar='m', type=int, help='random seed for repeatability (default=None)', default=None)
parser.add_argument('-i', '--iter', metavar='n', type=int, help='maximum number of iterations (default=100)', default=100)
parser.add_argument('-b', '--blobs', metavar='file', type=str, help='file containing blob parameters for synthetic data (default=Data/blobs.txt)', default='Data/blobs.txt')
parser.add_argument('-w', '--wait', metavar='sec', type=float, help='number of seconds to wait between display updates (default=1)', default=1)


def main(args):
    DISTANCES = {
        1: "euclidean",
        2: "manhattan",
        3: "cosine",
        4: "cityblock"
    }
    
    LINKAGES = {
        1: "single",
        2: "complete",
        3: "average",
        4: "ward"
    }
    
    while True:
        print("===================")
        print("     MAIN MENU     ")
        print("===================")
        print("Select an option to proceed:")
        print("0 - Exit")
        print("1 - Single Linkage")
        print("2 - Complete Linkage")
        print("3 - Average Linkage")
        print("4 - Ward Linkage")
        choice = int(input())
        if choice == 0:
            break
        elif choice < 4:
            print_options()
            distance = DISTANCES[int(input())]
            agglomerative_clustering(LINKAGES[choice], distance, args)
        elif choice == 4:
            agglomerative_clustering("ward", "euclidean", args)
        else:
            print("Invalid choice! Try again!")


def print_options():
    print("=======================")
    print("     DISTANCE MENU     ")
    print("=======================")
    print("1 - Euclidean")
    print("2 - Manhattan")
    print("3 - Cosine")
    print("4 - City Block")


def agglomerative_clustering(clustering_type: str, distance: str, args):
    print("============================")
    print(f"{clustering_type.upper()} LINKAGE AGGLOMERATIVE CLUSTERING")
    print("============================")

    # Setup problem
    np.random.seed(args.seed)  # initialize random number generator (for repeatability)
    x = generate_data(args.blobs)  # make synthetic 2D data

    # EDIT CODE HERE
    model = AgglomerativeClustering(n_clusters=args.k, linkage=clustering_type, compute_distances=True, affinity=distance)
    y = model.fit_predict(x)
    #pdb.set_trace()
    Z = hierarchy.linkage(x, clustering_type)
    plt.figure()
    dn = hierarchy.dendrogram(Z)
    plt.show()

    # Show results
    show_data(x, y)

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


def show_data(x, y):
    '''Display sample data (x) with corresponding labels (y) and cluster means (mu).'''
    plt.figure(1)
    plt.clf()
    s1 = plt.scatter(x[:, 0], x[:, 1], s=6, c=y)
    ax = plt.gca()
    ax.set_xlim(-10, 10)
    ax.set_ylim(-10, 10)
    ax.axis('off')
    plt.ion()
    plt.show()


if __name__ == '__main__':
    main(parser.parse_args())
