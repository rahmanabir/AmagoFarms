/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('RetailStores', {
		storeID: {
			type: DataTypes.INTEGER,
			allowNull: false,
			primaryKey: true,
			autoIncrement: true
		},
		address: {
			type: "ARRAY",
			allowNull: false
		},
		userID: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		createdAt: {
			type: DataTypes.DATEONLY,
			allowNull: true
		},
		updatedAt: {
			type: DataTypes.DATEONLY,
			allowNull: true
		}
	}, {
		tableName: 'RetailStores',
		freezeTableName: true,
		timestamps: false
	});
};
