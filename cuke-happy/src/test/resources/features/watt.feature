Feature: Watt happy cube formation

Scenario: Watt faces can be used to solve a cube
Given a face called 'watt0'
And a face called 'watt1'
And a face called 'watt2'
And a face called 'watt3'
And a face called 'watt4'
And a face called 'watt5'
When I say solve the cube
Then tell me the cube was solved
And print the solved cube


Scenario: Six compatible Watt faces can be solved in any sequence
Given a face called 'watt4'
And a face called 'watt2'
And a face called 'watt1'
And a face called 'watt5'
And a face called 'watt0'
And a face called 'watt3'
When I say solve for unique cubes
Then tell me the 1 unique cubes are possible
And print the unique cubes

