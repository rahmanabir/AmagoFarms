//Need to use ' Sequelize - Auto' to generate models 

const Sequelize = require('sequelize'); 
const db = require('../config/database');

const Users = db.define('users',{
    userID:{
        type: Sequelize.STRING
    },
    username:{
        type: Sequelize.STRING
    },
    password:{
        type: Sequelize.STRING
    },
    phone:{
        type: Sequelize.STRING
    },
    regDate:{
        type: Sequelize.STRING
    },
    userType:{
        type: Sequelize.STRING
    }
}); 

module.exports = Users; 