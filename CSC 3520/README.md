# CSC 3520 Homeworks
This folder holds all of my homework solutions for my CSC 3520 - Machine Learning (Fall 2022) course at Florida Southern College.

Quick Links: [Homework 1](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520#homework-1---naive-bayesian-classifier) | [Homework 2](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520#homework-2---decision-tree) | [Homework 3](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520#homework-3---decision-boundaries) | [Homework 4](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520#homework-4---k-nearest-neighbors-and-support-vector-machine)

## [Homework 1 - Naive Bayesian Classifier](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520/Homework%201)
Follows the guidelines outlined [here](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%20Guidelines/Homework%201.pdf) to implement a [naive bayesian classifier](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%201/naivebayes.py) that determines what category an article falls under based on the words in the article.

Total Length: 146 lines (including spaces and comments)

### Required Libraries
- numpy
- matplotlib (uses pyplot)
- sklearn (uses confusion_matrix from metrics)
### Passable Arguments
- -x / --training_data (path to the training data)
- -y / --training_labels (path to the training labels)
- -xt / --testing_data (path to the testing data)
- -yt / --testing_labels (path to the testing labels)
- -n / --newsgroups (path to the newsgroups file)
- -v / --vocabulary (path to the vocabulary file)

[Top](https://github.com/JacobKnox/Predicting-Spotify-Song-Popularity#readme)

## [Homework 2 - Decision Tree](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520/Homework%202)
Follows the guidelines outlined [here](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%20Guidelines/Homework%202.pdf) to implement a [decision tree](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%202/decisiontree.py) that predicts  whether or not someone has 
Parkinson’s based on dysphonia measurements.

Total Length: 56 lines (including spaces and comments)

### Required Libraries
- numpy
- matplotlib (uses pyplot)
- sklearn (uses DecisionTreeClassifier and plot_tree from tree and confusion_matrix from metrics)

[Top](https://github.com/JacobKnox/Predicting-Spotify-Song-Popularity#readme)

## [Homework 3 - Decision Boundaries](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520/Homework%203)
Follows the guidelines outlined [here](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%20Guidelines/Homework%203.pdf) to visual the [decision boundaries](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%203/decisionboundaries.py) created by a simple input-hidden-output (2-2-1) "neural network" with bias.

Total Length: 46 lines (including spaces and comments)

### Required Libraries
- numpy
- matplotlib (uses pyplot)
- sklearn (uses cartesian from utils.extmath)

[Top](https://github.com/JacobKnox/Predicting-Spotify-Song-Popularity#readme)

## [Homework 4 - K-Nearest Neighbors and Support Vector Machine](https://github.com/JacobKnox/Jacob-Knox-Projects/tree/main/CSC%203520/Homework%204)
Follows the guidelines outlined [here](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%20Guidelines/Homework%204.pdf) to implement a [k-nearest neighbors model](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%204/kdigits.py) and a [support vector machine](https://github.com/JacobKnox/Jacob-Knox-Projects/blob/main/CSC%203520/Homework%204/sdigits.py) on the MNIST hand-written digits dataset.

Total Length: 79 lines (including spaces and comments)

### Required Libraries
- numpy [kdigits.py only]
- matplotlib (uses pyplot) [kdigits.py only]
- sklearn (uses neighbors [kgitis.py only] and svm [sdigits.py only])
- tensorflow
- skimage [kdigits.py only]
### Passable Arguments
#### kdigits.py
- -s / --samples (number of testing samples to use)
- -n / --neighbors (number of neighbors to consider)
#### sdigits.py
- -s / --samples (number of samples to use)
- -c / --c (C argument to pass into the SVM)
- -k / --kernel (which kernel to use for the SVM)

[Top](https://github.com/JacobKnox/Predicting-Spotify-Song-Popularity#readme)