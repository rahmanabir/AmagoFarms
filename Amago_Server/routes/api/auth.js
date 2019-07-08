const express = require('express');
const uuid = require('uuid');
const moment = require('moment');
const router = express.Router();

//User Model Import
const db = require('../../config/database');
const Users = db.import('../../models/Users_updated');

module.exports = router;
