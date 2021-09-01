Assigment:
	- Build a reusable/scalable search algorithm that is not specific to an entity or entities but something generic that can be reused for any data set. For this assessment, use the dataset mentioned at the end of this document.
		-- Data can be pre-populated.
		-- Application to have a REST API endpoint for search - E.g /search
		-- Search can be performed on a single field or multiple fields.
		-- Search is case insensitive.
		-- In case of multiple field searches, it has to be an AND condition. E.g actor=’Hugh Jackman’ AND director = ‘Christopher Nolan’
		-- Sort option on the result set to be implemented.
		-- Brownie points -
			--- Implementing OR condition combined with AND.
			--- Pagination of results			

Technology Used:
	Java 16, Spring Boot, MySQL, JPA/Hibernate

Download and run the sql file from here[assessment.zip](https://github.com/RakeshKumar95/search-netflix-data/files/7088322/assessment.zip)
Dataset used: https://www.kaggle.com/shivamb/netflix-shows

Note: 
	- There are two apis 
		GET /data/{pageIndex}/{pageSize}
			- pageIndex Required - Starts from 0, Cannot be negative
			- pageSize Optional - Cannot be negative
			
		POST /data/search
			Body:
					{
						"pageIndex": 0, (Defaults to 0)
						"pageSize": 30, (Defaults to 30)
						"query" : "S-description%\"romance\",D-dateAdded>\"December 24, 2010\",S-type%\"Movie\"",
						"sorts": ["releaseYear"] (Default sorting on releaseYear, Desc)
					}
					

Building Query: 
	<OR>-<type>-<fieldName><operator><value>
	
	<OR> - Use OR if need to use OR condition, by default everything is AND condition
	<type> - Type of search key value. Possible values - (S for String, N for Number, D - Date)
	<fieldName> - Field Name - Ex. actor, director, releaseYear etc. (Use camel case)
	<operator> - Operator to be used for comparison. Possible value are 
				-- #	(In) (Not available for date(D) type)
				-- >	(Greater Than) (Not available for String(S) type)
				-- <	(Less than) (Not available for String(S) type)
				-- >=	(Greater than) (Not available for String(S) type)
				-- <=	(Less than) (Not available for String(S) type)
				-- =	(equals)
				-- !=	(Not equals)
				-- %	(Like) (Available only for String(S) Type)
				-- !%	(Not Like) (Available only for String(S) Type)
				
	Ex. 
		"S-description%\"romance\",D-dateAdded>\"December 24, 2010\",S-type%\"Movie\""
		"OR-S-description%\"romance\",D-dateAdded>\"December 24, 2010\",S-type%\"Movie\""
		"S-cast#\"Robert,Steve\",S-description%\"romance\",D-dateAdded>\"December 24, 2010\",S-type%\"Movie\""
		
		
Ex: Call Made
	POST http://localhost:8080/data/search 
					  Body : 
							{
								"pageIndex": 0,
								"pageSize": 30,
								"query" : "S-description%\"romance\",D-dateAdded>\"December 24, 2010\",S-type%\"Movie\"",
								"sorts": ["dateAdded", "releaseYear"]
							}
		
	Result: 
		{
			"pageIndex": 0,
			"count": 30,
			"totalPages": 3,
			"data": [
				{
					"showId": "s5601",
					"type": "Movie",
					"title": "Sierra Burgess Is A Loser",
					"director": "Ian Samuels",
					"cast": "Shannon Purser, Kristine Froseth, RJ Cyler, Noah Centineo, Loretta Devine, Giorgia Whigham, Alice Lee, Lea Thompson, Alan Ruck, Mary Pat Gleason, Chrissy Metz",
					"country": "United States",
					"dateAdded": "September 7, 2018",
					"releaseYear": 2018,
					"rating": "PG-13",
					"duration": "106 min",
					"listedIn": "Comedies, Romantic Movies",
					"description": "A wrong-number text sparks a virtual romance between a smart but unpopular teen and a sweet jock who thinks he's talking to a gorgeous cheerleader."
				},
				{
					"showId": "s1216",
					"type": "Movie",
					"title": "Care of Kancharapalem",
					"director": "Maha Venkatesh",
					"cast": "Subba Rao Vepada, Radha Bessy, Kesava Karri, Nithyasri Goru, Karthik Rathnam, Praneetha Patnaik, Mohan Bhagath, Praveena Paruchuri",
					"country": "India",
					"dateAdded": "September 6, 2019",
					"releaseYear": 2018,
					"rating": "TV-14",
					"duration": "142 min",
					"listedIn": "Comedies, Dramas, Independent Movies",
					"description": "From a schoolboy’s crush to a middle-aged bachelor’s office romance, four love stories spanning age, religion and status unfold in a small Indian town."
				},
				.....
				{
					"showId": "s4000",
					"type": "Movie",
					"title": "Maya Memsaab",
					"director": "Ketan Mehta",
					"cast": "Deepa Sahi, Farooq Shaikh, Raj Babbar, Shah Rukh Khan, Shreeram Lagoo, Sudha Shivpuri, Paresh Rawal, Raghuvir Yadav, Satyadev Dubey",
					"country": "France, United Kingdom, India",
					"dateAdded": "June 1, 2017",
					"releaseYear": 1993,
					"rating": "TV-14",
					"duration": "122 min",
					"listedIn": "Dramas, International Movies, Music & Musicals",
					"description": "A beautiful, wealthy woman’s insatiable appetite for romance leads to tragedy and a police investigation."
				}
			]
		}
		
