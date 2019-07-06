/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('Transactions', {
		tID: {
			type: DataTypes.INTEGER,
			allowNull: false,
			primaryKey: true,
			autoIncrement: true
		},
		storeID: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		RetailerID: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		WholesellerID: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		reqID: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		transactionTime: {
			type: DataTypes.INTEGER,
			allowNull: true
		},
		werehouseID: {
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
		tableName: 'Transactions',
		freezeTableName: true,
		timestamps: false
	});
};
