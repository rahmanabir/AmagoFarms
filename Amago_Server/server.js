const express = require('express');
const path = require('path');

const logger = require('./middleware/logger');
        
const server = express();

//Database Connection
const db = require('./config/database'); 

//Initiate Logger middle ware      
server.use(logger); 

//DB test 
db.authenticate()
  .then(() => {
    console.log('Connection has been established successfully.');
  })
  .catch(err => {
    console.error('Unable to connect to the database:', err);
  });

//Built in express body parsers won't work if placed below API routes
//Body Parser Middleware
// server.use(express.json()); 
// server.use(express.urlencoded({extended: false}));

// //User API routes
// server.use('/api/user', require('./routes/api/user'));

// //Set static folder  
// server.use(express.static(path.join(__dirname, 'public'))); 


const PORT = process.env.PORT || 5000; // Place this in config file

server.listen(PORT, () => console.log(`Server running on port ${PORT}`));
