const express = require('express');
const router = express.Router();
const moment = require('moment');

//User Model Import
const db = require('../../config/database');
const Harvest = db.import('../../models/Inventory');

// @route GET api/harvest/allHarvest
// @description Returns all harvests
// @access Public
router.get(
  '/allHarvest',
  (req, res) => Harvest.findAll().then(result => res.json(result)) // ! Can't handle large data load, needs to be fixed
);

// @route GET api/harvest/getHarvest/:id
// @description gets users harvest list
// @access Public
router.get('/getHarvest/:id', (req, res) =>
  Harvest.findAll({
    where: {
      userID: req.params.id
    }
  })
    .then(harvest => res.json(harvest))
    .catch(err => console.log(err))
);

// @route POST api/harvest/postHarvest
// @description posts a harvest to db
// @access Public
router.post('/postHarvest', (req, res) => {
  const newHarvset = {
    userID: req.body.userID,
    itemType: req.body.itemType,
    amount: req.body.amount,
    createdAt: moment().format(),
    updatedAt: moment().format()
  };

  let { userID, itemType, amount, createdAt, updatedAt } = newHarvset;
  // !Insert into Inventory Table
  Harvest.create({
    userID,
    itemType,
    amount,
    createdAt,
    updatedAt
  })
    .then(res.status(200).json({ msg: 'Harvest Added' }))
    .catch(err => console.log(err));
});

module.exports = router;
