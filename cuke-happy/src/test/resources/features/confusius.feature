Feature: Confusius happy cube formation

Scenario: Confusius faces can be used to solve a cube
Given a face called 'conf0'
And a face called 'conf1'
And a face called 'conf2'
And a face called 'conf3'
And a face called 'conf4'
And a face called 'conf5'
When I say solve the cube
Then tell me the cube was solved
And print the solved cube


Scenario: Six compatible Confusius faces can be solved in any sequence
Given a face called 'conf0'
And a face called 'conf2'
And a face called 'conf3'
And a face called 'conf5'
And a face called 'conf1'
And a face called 'conf4'
When I say solve for unique cubes
Then tell me the 1 unique cubes are possible
And print the unique cubes

