# kdigits.py
# Implementing K-nearest neighbors algorithm on MNIST dataset

import numpy as np
import matplotlib.pyplot as plt
from sklearn import neighbors
import tensorflow as tf
import skimage
import argparse

parser = argparse.ArgumentParser(description="Use a K-nearest neighbors algorithm to distinguish between written digits in the MNIST dataset.")
parser.add_argument('-s', '--samples', help='number of testing samples to use', default=10000)
parser.add_argument('-n', '--neighbors', help='number of nearest neighbors to use', default=3)

def main(args):
    # Setup parameters
    k = int(args.neighbors)
    num_test = int(args.samples)
 
	# Load data
    (x_train, y_train), (x_test, y_test) = tf.keras.datasets.mnist.load_data()
    x_train = x_train.reshape((60000, 28*28))
    x_test = x_test.reshape((10000, 28*28))

	# "Train" k-NN
    clf = neighbors.KNeighborsClassifier(k)
    clf.fit(x_train, y_train)

	# Show results
    pred = clf.predict(x_test[:(num_test - 1)])
    acc = clf.score(x_test[:(num_test - 1)], y_test[:(num_test - 1)])
    print(f'Accuracy for K-Nearest Neighbors on first {num_test} MNIST Samples: {acc}')
    example = x_test[np.where(pred != y_test[:(num_test - 1)])[0][0]]

    _, indices = clf.kneighbors(example.reshape(1, -1), k)
    for index in indices:
        example = np.vstack((example, x_train[index]))

    print(example.shape)
    example = example.reshape(k + 1, 28, 28)
    img = skimage.util.montage(example)
    plt.imshow(img, cmap="Greys")
    plt.show()            


if __name__ == "__main__":
	main(parser.parse_args())