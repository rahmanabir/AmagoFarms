/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('Inventory', {
		id: {
			type: DataTypes.INTEGER,
			allowNull: false,
			primaryKey: true
		},
		itemType: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		userID: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		amount: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		price: {
			type: DataTypes.INTEGER,
			allowNull: true
		},
		status: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		retailID: {
			type: DataTypes.INTEGER,
			allowNull: true
		},
		retailName: {
			type: DataTypes.TEXT,
			allowNull: true
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
		tableName: 'Inventory',
		freezeTableName: true,
		timestamps: false
	});
};
