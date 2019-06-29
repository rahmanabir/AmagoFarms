/* jshint indent: 1 */

module.exports = function(sequelize, DataTypes) {
	return sequelize.define('produceTable', {
		itemType: {
			type: DataTypes.INTEGER,
			allowNull: false,
			primaryKey: true,
			autoIncrement: true
		},
		seasonal: {
			type: DataTypes.BOOLEAN,
			allowNull: true
		},
		name: {
			type: DataTypes.TEXT,
			allowNull: false
		},
		description: {
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
		tableName: 'produceTable',
		freezeTableName: true,
		timestamps: false
	});
};
