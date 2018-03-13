import java.security.PublicKey
import java.security.Signature

import scala.collection.JavaConverters._
import scala.collection.mutable

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

    val txs: mutable.Buffer[Transaction#Output] = tx.getOutputs.asScala
    val utxos: mutable.Buffer[UTXO] = utxoPool.getAllUTXO.asScala


    if(txs.exists(x => utxos.contains(x))){
      /* we've met requirement one. Now we need to check if all the signatures are valid. */

      // val algorithmName = getAlgorithm(txs.head)

      // We are using SHA-256
      val sig = Signature.getInstance("SHA256withRSA")

      // transform public keys into Array[Byte]
      val txSigs: mutable.Buffer[Array[Byte]] = txs.map(x => x.address.getEncoded)

      // If any of the signatures are invalid, this data structure will be nonEmpty
      /** We should use the exists method here instead!!! */
      val filterValidSigs: mutable.Buffer[Array[Byte]] = txSigs.filterNot(x => sig.verify(x))

      /**
        * We need to check if the signatures on each input are valid.
        * There is a method to do this in the Crypto class.
        * Should probably use the Crypto class.
        *
        * NEED TO WORRY ABOUT EXCEPTION HANDLING!!!!!
        */
      if(filterValidSigs.nonEmpty){
        // If any of the signatures are invalid, we will not enter this this section.

        /** no UTXO is claimed multiple times by { @code tx} */
          // I'm not sure if this these objects are comparable because they are different data types.
        val diffUtxoAndTxs: mutable.Buffer[Transaction#Output] = txs.diff(utxos)

        if(diffUtxoAndTxs.nonEmpty){

          /** Returns true if there's a non-negative value in this collection */
          val filterNonNegative = txs.exists(x => x.value <= 0)

          /**
            * Make sure all output values are non-negative
            *
            * This is a problem!!! Need to look closer at what UTXO.java does!!!
            * Need to re-read book or watch lecture while looking at computer code.
            */
          if(!filterNonNegative){
            /** We should probably map to the values before summing this. */
            val sumTxs: Double = txs.sum.value

            /** THIS VALUE IS PROBABLY A PROBLEM!!! */
            val sumUtxos: Double = utxos.sum.hashCode()

            /** Either all the requirements are met, or they aren't. */
            if(sumTxs == sumUtxos) true else false
            } // END if all non-negative
          else false
          } // END all unique
        else false
        } // END nested if statements
      else false
      } // END inner if signatures are valid
    else false
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

