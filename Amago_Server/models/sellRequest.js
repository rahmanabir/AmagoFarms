/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define(
    'sellRequest',
    {
      reqid: {
        type: DataTypes.INTEGER,
        allowNull: false,
        primaryKey: true,
        autoIncrement: true
      },
      reqDate: {
        type: DataTypes.DATEONLY,
        allowNull: false
      },
      itemType: {
        type: DataTypes.INTEGER,
        allowNull: false
      },
      amount: {
        type: DataTypes.INTEGER,
        allowNull: false
      },
      price: {
        type: DataTypes.INTEGER,
        allowNull: false
      },
      reqStatus: {
        type: 'ARRAY',
        allowNull: false
      },
      updatedAt: {
        type: DataTypes.DATEONLY,
        allowNull: true
      },
      createdAt: {
        type: DataTypes.DATEONLY,
        allowNull: true
      },
      wholesellerID: {
        type: DataTypes.INTEGER,
        allowNull: false
      },
      userID: {
        type: DataTypes.INTEGER,
        allowNull: false
      }
    },
    {
      tableName: 'sellRequest',
      freezeTableName: true,
      timestamps: false
    }
  );
};
