const promise = require('bluebird');
const options = {
    promiseLib : promise,
    query: (e) => {

    }
}
const pgp = require('pg-promise')(options);
const types = pgp.pg.types;
types.setTypeParser(1114, function (stringValue){
    return stringValue;
});

const databaseConfig ={
    'host' : '172.18.0.1',
    'port' : 54320,
    'database' : 'delivery_db',
    'user' : 'user',
    'password' :  'admin'
};

const db = pgp(databaseConfig);

module.exports = db;
