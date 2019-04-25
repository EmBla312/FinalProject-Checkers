# FinalProject-Checkers
CS220 - Programming w/ Data Structures Final Group Project (Spring 2019)
Collaborators: Emma Blackwell, Claire Fettig, Mason Nightlinger

Description:

This project creates a Checkers game based on a General Tree, Priority Queue, and Linked List data structure. The Priority Queue and Linked List data structures work together to create an easy AI which plays Checkers against the player looking only at its best immediate move. The General Tree and Linked List data structures work together to create a hard AI which plays Checkers against the player. This time the AI plays its best move only after evaluating all possible moves for the next four turns. The General Tree applies the minimax concept where the AI is the Max and the player is the Min. The Checkers game operates on a 2D array which stores integer values representing the 32 invalid tiles of a Checkers board, tiles which have a red piece or white piece, tiles which have a red king or white king, and tiles which are empty but valid. The game can be played until either the AI or player can no longer make a move.

The following is an example of a board's initial setup. (AI = Red Team, Player = White Team)

--------------------------
Legend                                                     THE BOARD
--------------------------                              8 1 8 1 8 1 8 1
1 = Red Pawn                                            1 8 1 8 1 8 1 8                                              
2 = Red King                                            8 1 8 1 8 1 8 1                                                
3 = White Pawn                                          0 8 0 8 0 8 0 8
4 = White King                                          8 0 8 0 8 0 8 0
0 = Empty, valid Tile                                   3 8 3 8 3 8 3 8
8 = Invalid Tile                                        8 3 8 3 8 3 8 3
--------------------------                              3 8 3 8 3 8 3 8

                                               
                                               
                                                
                                                
                                                

