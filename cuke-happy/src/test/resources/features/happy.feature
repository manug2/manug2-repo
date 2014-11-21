Feature: Happy cube formation

Scenario: Six faces are needed to make a cube
Given a face called 'face1'
When I say solve the cube
Then give me message that the cube needs 6 faces


Scenario: Cube cannot be solved with two faces
Given a face called 'face1'
And a face called 'face2'
When I say solve the cube
Then give me message that the cube needs 6 faces

Scenario: Six faces can be used to solve a cube
Given a face called 'face0'
And a face called 'face1'
And a face called 'face2'
And a face called 'face3'
And a face called 'face4'
And a face called 'face5'
When I say solve the cube
Then tell me the cube was solved
And print the solved cube


Scenario: Six incompatible faces cannot be used to solve a cube
Given a face called 'face0'
And a face called 'face1'
And a face called 'face2'
And a face called 'face3'
And a face called 'face4'
And a face called 'face6'
When I say solve the cube
Then tell me I could not solve the cube

