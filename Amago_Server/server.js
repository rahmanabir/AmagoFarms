const express = require('express');
const server = express();

//EJS view engine initialization
server.set('view engine', 'ejs');

//Loading static assets for web retail
server.use(express.static(__dirname + '/public'));

// create application/x-www-form-urlencoded parser
const urlencodedParser = express.urlencoded({ extended: false }); // ! Problem has

//Mobile app Routes
const users = require('./routes/api/users');
const auth = require('./routes/api/auth');
const harvest = require('./routes/api/harvest');
const item = require('./routes/api/item');
const sellReq = require('./routes/api/sellReq');

//Web app Routes
const retailUsers = require('./controllers/retailUser');
const retailAuth = require('./controllers/retailAuth');
const retailDashboard = require('./controllers/retailDashboard');
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
/*
//Retail webapp routes
server.get('/register', (req, res) => {
  res.render('register', { name: 'Register for a retail account' });
});

server.post('/register', urlencodedParser, (req, res) => {
  var register = {
    first: req.body.firstName,
    last: req.body.lastName,
    phone: req.body.phone,
    password: req.body.inputPassword
  };
  console.log(register);
  res.render('login');

  //res.render('home',{
  //     userValue : student,
  //     topicHead : 'Student Form'
});
*/
//use routes
server.use('/api/users', users);
server.use('/api/auth', auth);
server.use('/api/harvest', harvest);
server.use('/api/item', item);
server.use('/api/sellRequest', sellReq);

server.use('/', retailUsers);
server.use('/', retailAuth);
server.use('/', retailDashboard);

const PORT = process.env.PORT || 5000; // TODO Place this in config file
server.listen(PORT, () => console.log(`Server running on port ${PORT}`));
