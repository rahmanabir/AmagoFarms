// const express = require('express');
// const path = require('path');

// const logger = require('./middleware/logger');
        
// const server = express();

// //Database Connection
// const db = require('./config/database'); 

// //Initiate Logger middle ware      
// server.use(logger); 

// //DB test 
// db.authenticate()
//   .then(() => {
//     console.log('Connection has been established successfully.');
//   })
//   .catch(err => {
//     console.error('Unable to connect to the database:', err);
//   });

// //Built in express body parsers won't work if placed below API routes
// //Body Parser Middleware
// server.use(express.json()); 
// server.use(express.urlencoded({extended: false}));

// // //User API routes
// // server.use('/api/user', require('./routes/api/user'));

// // //Set static folder  
// // server.use(express.static(path.join(__dirname, 'public'))); 


// const PORT = process.env.PORT || 5000; // Place this in config file

// server.listen(PORT, () => console.log(`Server running on port ${PORT}`));


const express = require('express');
const path = require('path');

// Database
const db = require('./config/database');

// Test DB
db.authenticate()
  .then(() => console.log('Database connected...'))
  .catch(err => console.log('Error: ' + err))

const server = express();

server.use(express.json()); 
server.use(express.urlencoded({extended: false}));

server.get('/', (req, res) => res.status(400).json({msg: `you seem lost, please read shin-api documentation`}));

server.use('/trans', require('./routes/api/transactions'));

const PORT = process.env.PORT || 5000; // Place this in config file
server.listen(PORT, () => console.log(`Server running on port ${PORT}`));


