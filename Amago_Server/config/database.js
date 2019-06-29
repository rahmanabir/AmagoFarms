const Sequelize = require('sequelize');


module.exports = new Sequelize('d62n9ee4aotom4', 'uleqnkzktovcaj', '31d9bc2ded7f2ebe434feaad96e3e3b12e2e00623a5c5e6a85fdb8cdd6c35c69', {
  host: 'ec2-54-228-252-67.eu-west-1.compute.amazonaws.com',
  dialect: 'postgres',
  port: 5432, 
  ssl: true,
  // operatorAliases: false, 

  dialectOptions: {
    "ssl": {"require":true }
  },

  pool: {
      max: 5,
      min: 0, 
      acquire: 30000, 
      idle: 10000
  },
});



// module.exports = new Sequelize('postgres://uleqnkzktovcaj:31d9bc2ded7f2ebe434feaad96e3e3b12e2e00623a5c5e6a85fdb8cdd6c35c69@ec2-54-228-252-67.eu-west-1.compute.amazonaws.com:5432/d62n9ee4aotom4');