# kindwordsapp

## Initial Collaboration signatures
Name: Brian Ojoh Mile   Group number: 21   project name: Kind Words  
Name: Justin Mullins Group Number: 21 Project Name: Kind Words  
Name: Anjali Sharma Group Number: 21 Project Name: Kind Words  
Name: Tsewang Thilly Group Number: 21 Project Name: Kind Words  

## Planning 
* Project description 
	* With the Quarantine, people are feeling down. Write an app that lets people write letters anonymously and let other users respond with positivity and encouragement
	* To keep the good vibes, make sure you incorporate a punishment system for those that just want to bring others down.
	
* Roles 
	* UI Design Programmer
		* Everything UI related (xml files, icons, fonts, etc)
		* Testing
	* Kotlin Programmer 
		* Handles client - app interactions
		* Handles app - database interactions 
		* Testing 
	* Project Manager 
		* Directs general flow of development 
		* Develops program skeletons and TODO directions for other contributors 
		* Testing 

* Currently Allocated roles 
	* Brian : Project Manager, Kotlin programmer 
	* Anjali : Kotlin programmer 
	* Justin : Kotlin programmer 
	* Thilly : UI Design Programmer 

* Features 
	* Publicly post anonymous cards (letters) than can be replied to by other users of the app 
	* Report users who post offensive content 
	* Cards disappear every 24hrs 
	* Clients are allowed to delete cards but cannot edit them 
	* Reported cards cannot be deleted 
	
* Admin
	* Collect app violation reports through a google account 
	* Permanent or temporary ban, or strike the accounts of offenders 
	
* Register Page 
	* Should clients register with both an email and password, just a unique email, or just a unique password?
	* Either way, we need one of them to generate a unique identity per client, allowing them access to previous sessions or database information
	* Validation of acceptable inputs 

* Login Page 
	* Validation of acceptable inputs, and user credentials exist in database 

* Public Feeds Page 
	* Publicly available letter cards are randomized before displayed to the client 
	* Client's displayed username is also randomized (name of an animal, poet, movie character, unlimited ideas) to keep their anonymity and prevent other users from identifying them. The client's unique id is never publicly displayed
	* Tinder style cards for publicly posted letters 
	* Swipe right to reply to a letter 
	* Swipe left to skip/ignore a letter 
	* swipe down to report a letter 
	* Letter cards expire after every 24 hours

* User (private) Feeds Page 
	* Two sections 
		* My Cards section 
			* A client can view/delete their posted cards 
			* A client can view replies to their posted cards 
			* Cards disappear after 24hrs 
		* My Replies section
			* Client can view cards they replied to, including their replies and subsequent replies (like a thread). But client cannot delete the cards  
			* Cards disappear after 24hrs 

* User settings Page 
	* client can delete their account (removing it from the database, useful for testing)
	* Deleting an account re-directs them to the register activity (aka register page)

