const express = require('express');
const router = express.Router();
const moment = require('moment');

//sellRequest model import
const db = require('../../config/database');
const SellReq = db.import('../../models/sellRequest');

// @route GET api/sellRequest/all
// @description Returns all sell requests
// @access Public
router.get('/all', (req, res) => {
  SellReq.findAll().then(result => res.json);
});

// @route GET api/sellRequest/getUserSellReq/:id
// @description Returns sell requests for a specific user
// @access Public
router.get('/getUserSellReq/:id', (req, res) => {
  SellReq.findAll({
    where: {
      userID: req.params.id
    }
  })
    .then(sell => res.json(sell))
    .catch(err => console.log(err));
});

// @route GET api/sellRequest/getSellRequest/:id
// @description Returns a particular sell request
// @access Public
router.get('/getSellRequest/:id', (req, res) => {
  SellReq.findAll({
    where: {
      reqid: req.params.id
    }
  })
    .then(sell => res.json(sell))
    .catch(err => console.log(err));
});

router.post('/postSellRequest', (req, res) => {
  const newRequest = {
    userID: req.body.userID,
    itemType: req.body.itemType,
    amount: req.body.amount,
    reqDate: moment().format(),
    price: req.body.price,
    reqStatus: req.body.reqStatus,
    createdAt: moment().format(),
    updatedAt: moment().format()
  };

  let {
    userID,
    itemType,
    amount,
    reqDate,
    price,
    reqStatus,
    createdAt,
    updatedAt
  } = newRequest;

  SellReq.create({
    userID,
    itemType,
    amount,
    reqDate,
    price,
    reqStatus,
    createdAt,
    updatedAt
  })
    .then(res.status(200).json({ msg: 'Sell Request Added' }))
    .catch(err => console.log(err));
});
module.exports = router;
