Feature: Unique happy cube formation

Scenario: Find unique cubes
Given a face called 'face0'
And a face called 'face1'
And a face called 'face2'
And a face called 'face3'
And a face called 'face4'
And a face called 'face5'
When I say solve for unique cubes
Then tell me the 1 unique cubes are possible
And print the unique cubes


Scenario: Six compatible faces can be solved in any sequence
Given a face called 'face5'
And a face called 'face2'
And a face called 'face4'
And a face called 'face3'
And a face called 'face0'
And a face called 'face1'
When I say solve for unique cubes
Then tell me the 1 unique cubes are possible
And print the unique cubes

