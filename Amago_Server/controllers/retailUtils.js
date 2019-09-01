const express = require('express');
const router = express.Router();
const moment = require('moment');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

router.get('/forgot-password', (req, res) => {
  res.render('forgot-password');
});

router.get('/404', (req, res) => {
  res.render('404');
});

router.get('/blank', (req, res) => {
  res.render('blank', {
    msg: 'Purchase Complete, Requested for delivery!'
  });
});

router.get('/charts', (req, res) => {
  res.render('charts');
});

router.get('/tables', (req, res) => {
  res.render('tables');
});

module.exports = router;
