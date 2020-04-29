/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const { Contract } = require('fabric-contract-api');
const crypto = require('crypto');
const myCollectionName = 'CollectionOne';

class MyPrivateAssetContract extends Contract {

    async myPrivateAssetExists(ctx, myPrivateAssetId) {
        const buffer = await ctx.stub.getPrivateDataHash(myCollectionName, myPrivateAssetId);
        return (!!buffer && buffer.length > 0);
    }

    async createMyPrivateAsset(ctx, myPrivateAssetId) {
        const exists = await this.myPrivateAssetExists(ctx, myPrivateAssetId);
        if (exists) {
            throw new Error(`The asset my private asset ${myPrivateAssetId} already exists`);
        }

        const privateAsset = {};

        const transientData = ctx.stub.getTransient();
        if (transientData.size === 0 || !transientData.has('privateValue')) {
            throw new Error('The privateValue key was not specified in transient data. Please try again.');
        }
        privateAsset.privateValue = transientData.get('privateValue').toString('utf8');

        await ctx.stub.putPrivateData(myCollectionName, myPrivateAssetId, Buffer.from(JSON.stringify(privateAsset)));
    }

    async readMyPrivateAsset(ctx, myPrivateAssetId) {
        const exists = await this.myPrivateAssetExists(ctx, myPrivateAssetId);
        if (!exists) {
            throw new Error(`The asset my private asset ${myPrivateAssetId} does not exist`);
        }
        let privateDataString;
        const privateData = await ctx.stub.getPrivateData(myCollectionName, myPrivateAssetId);
        privateDataString = JSON.parse(privateData.toString());
        return privateDataString;
    }

    async updateMyPrivateAsset(ctx, myPrivateAssetId) {
        const exists = await this.myPrivateAssetExists(ctx, myPrivateAssetId);
        if (!exists) {
            throw new Error(`The asset my private asset ${myPrivateAssetId} does not exist`);
        }
        const privateAsset = {};

        const transientData = ctx.stub.getTransient();
        if (transientData.size === 0 || !transientData.has('privateValue')) {
            throw new Error('The privateValue key was not specified in transient data. Please try again.');
        }
        privateAsset.privateValue = transientData.get('privateValue').toString('utf8');

        await ctx.stub.putPrivateData(myCollectionName, myPrivateAssetId, Buffer.from(JSON.stringify(privateAsset)));
    }

    async deleteMyPrivateAsset(ctx, myPrivateAssetId) {
        const exists = await this.myPrivateAssetExists(ctx, myPrivateAssetId);
        if (!exists) {
            throw new Error(`The asset my private asset ${myPrivateAssetId} does not exist`);
        }
        await ctx.stub.deletePrivateData(myCollectionName, myPrivateAssetId);
    }

    async verifyMyPrivateAsset(ctx, myPrivateAssetId, objectToVerify) {

        // Convert provided object into a hash
        const hashToVerify = crypto.createHash('sha256').update(objectToVerify).digest('hex');
        const pdHashBytes = await ctx.stub.getPrivateDataHash(myCollectionName, myPrivateAssetId);
        if (pdHashBytes.length === 0) {
            throw new Error('No private data hash with the key: ' + myPrivateAssetId);
        }

        const actualHash = pdHashBytes.toString('hex');

        // Compare the hash calculated (from object provided) and the hash stored on public ledger
        if (hashToVerify === actualHash) {
            return true;
        } else {
            return false;
        }
    }


}

module.exports = MyPrivateAssetContract;
