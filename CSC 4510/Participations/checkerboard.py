# checkerboard.py
# Graphics utilities for drawing and manipulating checkerboards.
#
# This module was initially developed for solving the N-queens problem
# with a genetic algorithm.

from graphics import *

ROWS = 8  # number of rows in the checkerboard
COLS = 8  # number of columns in the checkerboard
SIZE = 45  # size of each checkerboard square
MARGIN = 20  # margin between figure boundary and checkerboard (in pixels)
COLOR_BACKGROUND = '#ffffff'
COLOR_LINES = '#000000'
COLOR_SQUARE1 = '#ffffff'
COLOR_SQUARE2 = '#000000'
COLOR_QUEEN = '#ff0000'


def add(gui, x, rows=ROWS, cols=COLS, size=SIZE, margin=MARGIN):
    '''Add queens (as circles) to the board at positions specified in the list x.'''
    queens = []
    for row in range(len(x)):
        queen = Circle(Point(margin + (x[row] + 0.5) * size, margin + (row + 0.5) * size), size // 3)
        queen.setOutline(COLOR_QUEEN)
        queen.setFill(COLOR_QUEEN)
        queen.draw(gui)
        queens.append(queen)

    return queens


def draw(rows=ROWS, cols=COLS, size=SIZE, margin=MARGIN):
    '''Draw the checkerboad with specific dimensions.'''
    # Initialize graphics window
    width = size * cols + 2 * margin
    height = size * rows + 2 * margin
    gui = GraphWin(title="Checkerboard", width=width, height=height)
    gui.setBackground(COLOR_BACKGROUND)

    # Add checkerboard
    rect = Rectangle(Point(margin, margin), Point(width - margin, height - margin))
    rect.setWidth(3)
    rect.setOutline(COLOR_LINES)

    for row in range(rows):
        if row % 2 == 0:
            for col in range(0, cols, 2):
                p1 = Point(margin + col * size, margin + row * size)
                p2 = Point(margin + (col + 1) * size, margin + (row + 1) * size)
                r = Rectangle(p1, p2)
                r.setWidth(1)
                r.setOutline(COLOR_SQUARE1)
                r.setFill(COLOR_SQUARE1)
                r.draw(gui)
        else:
            for col in range(1, cols, 2):
                p1 = Point(margin + col * size, margin + row * size)
                p2 = Point(margin + (col + 1) * size, margin + (row + 1) * size)
                r = Rectangle(p1, p2)
                r.setWidth(1)
                r.setOutline(COLOR_SQUARE1)
                r.setFill(COLOR_SQUARE1)
                r.draw(gui)

    for row in range(rows):
        if row % 2 == 1:
            for col in range(0, cols, 2):
                p1 = Point(margin + col * size, margin + row * size)
                p2 = Point(margin + (col + 1) * size, margin + (row + 1) * size)
                r = Rectangle(p1, p2)
                r.setWidth(1)
                r.setOutline(COLOR_SQUARE2)
                r.setFill(COLOR_SQUARE2)
                r.draw(gui)
        else:
            for col in range(1, cols, 2):
                p1 = Point(margin + col * size, margin + row * size)
                p2 = Point(margin + (col + 1) * size, margin + (row + 1) * size)
                r = Rectangle(p1, p2)
                r.setWidth(1)
                r.setOutline(COLOR_SQUARE2)
                r.setFill(COLOR_SQUARE2)
                r.draw(gui)

    rect.draw(gui)

    return gui


def move(gui, queens, x, rows=ROWS, cols=COLS, size=SIZE, margin=MARGIN):
    '''Move queens to new locations (x) on the board.'''
    for i in range(len(queens)):
        x0 = queens[i].getCenter().getX()
        col0 = (x0 - margin) / size - 0.5
        dx = (x[i] - col0) * size
        queens[i].move(dx, 0)


def remove(queens):
    '''Undraw queens from board.'''
    for queen in queens:
        queen.undraw()


if __name__ == '__main__':
    # Draw a sample checkerboard
    gui = draw()
    queen_locations = [7, 1, 2, 3, 4, 5, 6, 6]
    queens = add(gui, queen_locations)

    # Wait for user input to close the GUI
    while True:
        key = gui.checkKey()
        if key == 'Escape':
            break  # exit the loop when 'Esc' is pressed
    gui.close()