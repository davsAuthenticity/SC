pragma solidity ^0.4.7;

contract davs {

    // list to store qr code hash
    mapping (bytes32 => int) qrList;

    // add qr hash in list
    function addQR (bytes32 val) public {
        qrList[val] = 1;
    }

    // returns 1 if qr hash exists else return -1 
    function verify (bytes32 val) public constant returns (int) {
        return qrList[val];
    }

}