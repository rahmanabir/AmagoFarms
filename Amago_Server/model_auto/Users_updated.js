/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('Users_updated', {
		userID: {
			type: DataTypes.INTEGER,
			allowNull: false,
			primaryKey: true,
			autoIncrement: true
		},
		password: {
			type: DataTypes.TEXT,
			allowNull: false
		},
		createdAt: {
			type: DataTypes.DATEONLY,
			allowNull: false
		},
		username: {
			type: DataTypes.TEXT,
			allowNull: false
		},
		userType: {
			type: DataTypes.INTEGER,
			allowNull: false
		},
		updatedAt: {
			type: DataTypes.DATEONLY,
			allowNull: true
		},
		phone: {
			type: DataTypes.INTEGER,
			allowNull: true
		}
	}, {
		tableName: 'Users_updated',
		freezeTableName: true,
		timestamps: false
	});
};
