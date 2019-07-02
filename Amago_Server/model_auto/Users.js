/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('Users', {
		userID: {
			type: DataTypes.INTEGER,
			allowNull: false,
			primaryKey: true
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
		}
	}, {
		tableName: 'Users',
		freezeTableName: true,
		timestamps: false
	});
};
