/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('Werehouse', {
		werehouseID: {
			type: DataTypes.INTEGER,
			allowNull: false,
			primaryKey: true,
			autoIncrement: true
		},
		curCapacity: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		storageRooms: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		maxCapacity: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		address: {
			type: DataTypes.TEXT,
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
		tableName: 'Werehouse',
		freezeTableName: true,
		timestamps: false
	});
};
