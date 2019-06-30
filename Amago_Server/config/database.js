const Sequelize = require('sequelize');


//Change connection details for local db
//config files need to be hidden from git updates. Passwords need to be stored on a different file. 
module.exports = new Sequelize('amago', 'postgres', '8953814', {
  host: 'localhost',
  dialect: 'postgres', 
  operatorAliases: false, 

  pool: {
      max: 5,
      min: 0, 
      acquire: 30000, 
      idle: 10000
  },
});

