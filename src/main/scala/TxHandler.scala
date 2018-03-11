import scala.collection.JavaConverters._

class TxHandler(val utxoPool: UTXOPool) {
  /**
    * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
    * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
    * constructor.
    */
  // def this(utxoPool: UTXOPool) {
  // }

  /**
    * @return true if:
    *         (1) all outputs claimed by { @code tx} are in the current UTXO pool,
    *         (2) the signatures on each input of { @code tx} are valid,
    *         (3) no UTXO is claimed multiple times by { @code tx},
    *         (4) all of { @code tx}s output values are non-negative, and
    *         (5) the sum of { @code tx}s input values is greater than or equal to the sum of its output
    *             values; and false otherwise.
    */
  def isValidTx(tx: Transaction): Boolean = {

    // We need to get everything

    val txs = tx.getOutputs.asScala
    val utxos = utxoPool.getAllUTXO.asScala


    if(txs.exists(x => utxos.contains(x))){
      /* we've met requirement one. Now we need to check if all the signatures are valid. */

      /** We need to check if the signatures on each input are valid */
      if(4 == 4){

      } // END inner if signatures are valid
    } // All outputs claimed by tx are in current UTXO pool


  } // END isValidTx()

  /**
    * Handles each epoch by receiving an unordered array of proposed transactions, checking each
    * transaction for correctness, returning a mutually valid array of accepted transactions, and
    * updating the current UTXO pool as appropriate.
    */
  def handleTxs(possibleTxs: Array[Transaction]): Array[Transaction] = {


  }



} // END TxHandler class

