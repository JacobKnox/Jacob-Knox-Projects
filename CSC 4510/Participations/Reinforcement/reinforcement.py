# reinforcement.py
# Use reinforcement learning to help Robby the Robot navigate a maze.

import argparse
from graphics import *
import matplotlib.pyplot as plt
from matplotlib.colors import Normalize
import numpy as np
import pdb
from robby import World
import time

parser = argparse.ArgumentParser(description="Use reinforcement learning to help Robby the Robot navigate a maze")
parser.add_argument('txtfile', help="text file containing the maze, encoded as several rows of integers")
parser.add_argument('-g', '--gamma', help='discount factor (default=0.9)', default=0.9)
parser.add_argument('-v', '--verbose', action='store_true', help="display updates in terminal")

CMAP = plt.cm.get_cmap('bwr')
MAXVALUE = 100
MINITER = 1e2
MAXITER = 1e5
NORM = Normalize(vmin=0, vmax=MAXVALUE)
KEY = {'.': 0, 'S': 1, 'G': 2, '#': -1}


def main(txtfile, **kwargs):
    # Setup numpy stuff
    np.set_printoptions(precision=2)
    np.random.seed(40)

    # Read maze from file
    with open(txtfile, 'r') as f:
        maze = [[KEY[ch] for ch in line.strip()] for line in f]

    # Create Robby's world based on the input maze
    rows, cols = len(maze), len(maze[0])
    rw = World(rows, cols)
    rw.graphicsOn()

    # Add maze elements
    values = addheatmap(rw, rows, cols)  # initialize a heatmap of values
    for row in range(rows):
        for col in range(cols):
            if maze[row][col] == KEY['#']:
                addwall(rw, row, col)
            elif maze[row][col] == KEY['S']:
                addstart(rw, row, col)
                rw.goto(row, col)
                maze[row][col] = KEY['.']
            elif maze[row][col] == KEY['G']:
                addgoal(rw, row, col)
    # print(*maze)

    # Play in Robby's world
    while True:
        key = rw.checkKey()
        if key:
            if key == "Escape":
                break
            if key == "d":
                pdb.set_trace()
            elif key == "Up":
                rw.north()
            elif key == "Right":
                rw.east()
            elif key == "Down":
                rw.south()
            elif key == "Left":
                rw.west()
            elif key == 'v':
                values = np.random.randint(0, MAXVALUE, (rows, cols))
                # values = np.zeros((rows, cols))
                valueiteration(rw, maze, values, kwargs['gamma'])
            elif key == 'r':
                # Let robby run around using optimal policy based on value
                gorobbygo(rw, maze, values)


def addgoal(rw, row, col):
    '''Add wall to the maze at a specified row and column.'''
    wid, hei = rw.cellw, rw.cellh
    topleft = Point((col + 1) * wid, (row + 1) * hei)
    bottomright = Point((col + 2) * wid, (row + 2) * hei)
    rect = Rectangle(topleft, bottomright)
    rect.setOutline(color_rgb(255, 215, 0))
    rect.setWidth(5)
    rect.draw(rw)


def addheatmap(rw, rows, cols):
    '''Add a heatmap of values to Robby's World by creating an array of colored Rectangle objects.'''
    values = 0.5 * MAXVALUE * np.ones((rows, cols))
    wid, hei = rw.cellw, rw.cellh
    squares = []
    for row in range(rows):
        rowsquares = []
        for col in range(cols):
            topleft = Point((col + 1) * wid, (row + 1) * hei)
            bottomright = Point((col + 2) * wid, (row + 2) * hei)
            rect = Rectangle(topleft, bottomright)
            rgba = CMAP(NORM(values[row, col]), bytes=True)
            rect.setFill(color_rgb(rgba[0], rgba[1], rgba[2]))
            rect.draw(rw)
            rowsquares.append(rect)
        squares.append(rowsquares)

    rw.items.insert(0, squares)  # store the grid of Rectangle objects in the gui

    return values


def addstart(rw, row, col):
    '''Add wall to the maze at a specified row and column.'''
    wid, hei = rw.cellw, rw.cellh
    topleft = Point((col + 1) * wid, (row + 1) * hei)
    bottomright = Point((col + 2) * wid, (row + 2) * hei)
    rect = Rectangle(topleft, bottomright)
    rect.setOutline(color_rgb(0, 255, 0))
    rect.setWidth(5)
    rect.draw(rw)


def addwall(rw, row, col):
    '''Add wall to the maze at a specified row and column.'''
    wid, hei = rw.cellw, rw.cellh
    topleft = Point((col + 1) * wid, (row + 1) * hei)
    bottomright = Point((col + 2) * wid, (row + 2) * hei)
    rect = Rectangle(topleft, bottomright)
    rect.setFill(color_rgb(0, 0, 0))
    rect.draw(rw)


def futurevalue(state, action, values):
    '''Estimate the future value of the next state given the current state (row, col) and action.'''
    # ENTER CODE HERE
    row, col = state
    
    if action == 'N':
        newrow, newcol = row - 1, col
    elif action == 'E':
        newrow, newcol = row, col + 1
    elif action == 'S':
        newrow, newcol = row + 1, col
    elif action == 'W':
        newrow, newcol = row, col - 1
    
    return values[newrow][newcol]


def getbestaction(maze, values, state, actions):
    '''Determine the best action given a maze with corresponding values, the current state, and a list (string) of possible actions.'''
    if len(actions) == 0:
        print('Robby is stuck!')
        return ''

    row, col = state
    bestvalue = -1e6
    bestaction = ''
    
    # ENTER CODE HERE
    for action in actions:
        if action == 'N' and values[row - 1][col] > bestvalue:
            newrow, newcol = row - 1, col
        elif action == 'E' and values[row][col + 1] > bestvalue:
            newrow, newcol = row, col + 1
        elif action == 'S' and values[row + 1][col] > bestvalue:
            newrow, newcol = row + 1, col
        elif action == 'W' and values[row][col - 1] > bestvalue:
            newrow, newcol = row, col - 1
        
        if maze[newrow][newcol] == KEY['G']:
            return action
        
        if values[newrow][newcol] > bestvalue:
            bestaction = action
            bestvalue = values[newrow][newcol]
        

    return bestaction


def getvalidactions(maze, state, lastaction=None):
    '''Determine the list (string) of valid actions given the current state (row, col) in the maze.
    If lastaction is provided, do not allow Robby to repeat the previous action (in order to prevent running in circles!).'''
    rows, cols = len(maze), len(maze[0])
    row, col = state
    actions = ''

    # ENTER CODE HERE
    if row > 0 and maze[row - 1][col] != KEY['#'] and lastaction != 'S':
        actions += 'N'
    if col < (cols - 1) and maze[row][col + 1] != KEY['#'] and lastaction != 'W':
        actions += 'E'
    if row < (rows - 1) and maze[row + 1][col] != KEY['#'] and lastaction != 'N':
        actions += 'S'
    if col > 0 and maze[row][col - 1] != KEY['#'] and lastaction != 'E':
        actions += 'W'

    return actions


def gorobbygo(rw, maze, values):
    '''Watch robby run around the maze using optimal policy based on value iteration.'''
    action = None
    while maze[rw.robbyRow][rw.robbyCol] != KEY['G']:
        # ENTER CODE HERE
        state = (rw.robbyRow, rw.robbyCol)
        validactions = getvalidactions(maze, state, action)
        action = getbestaction(maze, values, state, validactions)
        if action == '':
            break
        move(rw, action)


def move(rw, action, pause=0.35):
    '''Take action in Robby's World.'''
    if action == 'N':
        rw.north()
    elif action == 'E':
        rw.east()
    elif action == 'S':
        rw.south()
    elif action == 'W':
        rw.west()

    time.sleep(pause)


def reward(state, action, maze):
    '''Reward function based on current state (row, col) and action.'''
    # ENTER CODE HERE
    row, col = state
    
    if action == 'N':
        newrow, newcol = row - 1, col
    elif action == 'E':
        newrow, newcol = row, col + 1
    elif action == 'S':
        newrow, newcol = row + 1, col
    elif action == 'W':
        newrow, newcol = row, col - 1
        
    if maze[newrow][newcol] == KEY['G']:
        return MAXVALUE
    
    return 0


def update(rw, values):
    '''Update Robby's World by displaying the heatmap of values.'''
    squares = rw.items[0]  # extract the grid of Rectangle objects
    for row in range(values.shape[0]):
        for col in range(values.shape[1]):
            rgba = CMAP(NORM(values[row, col]), bytes=True)
            squares[row][col].setFill(color_rgb(rgba[0], rgba[1], rgba[2]))


def valueiteration(rw, maze, values, gamma, verbose = True):
    '''Perform reinforcement learning via value iteration.'''
    update(rw, values)
    rows, cols = values.shape
    squares = rw.items[0]
    
    # ENTER CODE HERE
    i = 0
    nochange = 0
    while(i < MAXITER):
        state = (np.random.randint(rows), np.random.randint(cols))
        j, k = state
        if maze[j][k] == KEY['#']:
            continue
        actions = getvalidactions(maze, state)
        numactions = len(actions)
        if numactions == 0:
            continue
        Q = np.zeros(numactions,)
        for l in range(0, numactions):
            Q[l] = reward(state, actions[l], maze) + gamma * futurevalue(state, actions[l], values)
        Qmax = max(Q)
        if(abs(values[j][k] - Qmax) < 1):
            nochange += 1
        else:
            nochange = 0
        values[j][k] = Qmax
        rgba = CMAP(NORM(Qmax), bytes=True)
        squares[j][k].setFill(color_rgb(rgba[0], rgba[1], rgba[2]))
        if verbose:
            print(f'iter={i} state={state}, actions={actions}, Q={Q}, V={Qmax}')
        if nochange == 100 and i > MINITER:
            break
        i += 1
    
    


if __name__ == '__main__':
    main(**vars(parser.parse_args()))
