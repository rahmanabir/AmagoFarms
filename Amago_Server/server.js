const express = require('express');
const server = express();

//Routes
const users = require('./routes/api/users');
const auth = require('./routes/api/auth');
const harvest = require('./routes/api/harvest');
const item = require('./routes/api/item');
const sellReq = require('./routes/api/sellReq');

//Logs server changes for debug
const logger = require('./middleware/logger');
server.use(logger);

//Body Parser Middleware
server.use(express.json());
server.use(express.urlencoded({ extended: false }));

// DB config
const db = require('./config/database');

// DB Connection
/*
! Connected to amagoProduction DB server
*/
db.authenticate()
  .then(() => console.log('Database connected...'))
  .catch(err => console.log('Error: ' + err));

//use routes
server.use('/api/users', users);
server.use('/api/auth', auth);
server.use('/api/harvest', harvest);
server.use('/api/item', item);
server.use('/api/sellRequest', sellReq);

const PORT = process.env.PORT || 5000; // TODO Place this in config file
server.listen(PORT, () => console.log(`Server running on port ${PORT}`));
