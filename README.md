# WebAPI Developer
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction.
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

Solve using Spring Boot
Create a RESTful endpoint
Make up a data set to best demonstrate your solution
Check solution into GitHub

# Assumptions
* An amount of 50 is not included in the one point calculation
* Points 51 to 100 are inclusive, this satisfies the 
50 points over 100
* Didn't need to deal with scopes, service by default is Singleton