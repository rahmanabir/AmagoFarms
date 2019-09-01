const itemKeys = (req, res) => {
  key = [
    'Potato',
    'Onion',
    'Tomato',
    'Chilli',
    'Legume',
    'Cabbage',
    'Carrot',
    'Apple',
    'Banana',
    'Cauliflower',
    'Corn',
    'Grape',
    'Lemon',
    'Watermelon',
    'Mushroom',
    'Peanut',
    'Orange',
    'Papaya',
    'Peas',
    'Pineapple',
    'Pomegranate',
    'Gralic'
  ];
  return key[req];
  next();
};

exports.module = itemKeys;
