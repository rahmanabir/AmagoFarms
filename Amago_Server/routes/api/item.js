const express = require('express');
const router = express.Router();
const moment = require('moment');

//User Model Import
const db = require('../../config/database');
const Item = db.import('../../models/produceTable');

// @route GET api/item/allItems
// @description Returns all items
// @access Public
router.get(
  '/allItems',
  (req, res) => Item.findAll().then(result => res.json(result)) // ! Can't handle large data load, needs to be fixed
);

router.post('/postItem', (req, res) => {
  const newItem = {
    itemType: req.body.itemType,
    seasonal: req.body.seasonal,
    name: req.body.name,
    description: req.body.description,
    createdAt: moment().format(),
    updatedAt: moment().format()
  };

  let { itemType, seasonal, name, description, createdAt, updatedAt } = newItem;
  // !Insert into produceTable
  Item.create({
    itemType,
    seasonal,
    name,
    description,
    createdAt,
    updatedAt
  })
    .then(res.status(200).json({ msg: 'Item Added' }))
    .catch(err => console.log(err));
});

router.get('/getItem/:id', (req, res) => {
  Item.findAll({
    where: {
      itemType: req.params.id
    }
  })
    .then(item => res.json(item))
    .catch(err => console.log(err));
});
module.exports = router;
