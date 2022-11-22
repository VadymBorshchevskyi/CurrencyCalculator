package ai.codetest.currencycalculator.ui.adapters

import ai.codetest.currencycalculator.R
import ai.codetest.currencycalculator.biz.models.Operation
import ai.codetest.currencycalculator.databinding.HistoryItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter  : ListAdapter<Operation, HistoryViewHolder>(OPERATION_COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder = HistoryViewHolder(parent)

    override fun onBindViewHolder(
        holder: HistoryViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else{
            for (payloadList in payloads) {
                if (payloadList is List<*>) {
                    for (payload in payloadList) {
                        when (payload) {
                            is TimestampDataChange -> {
                                holder.bindDate(getItem(position))
                            }
                            is ResultDataChange -> {
                                holder.bindResult(getItem(position))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(viewHolder: HistoryViewHolder, position: Int) {
        viewHolder.bindTo(getItem(position))
    }

    companion object {

        val OPERATION_COMPARATOR = object : DiffUtil.ItemCallback<Operation>() {
            override fun areContentsTheSame(oldItem: Operation, newItem: Operation): Boolean {
                return oldItem.originAmount == newItem.originAmount &&
                        oldItem.originCurrencyCode == newItem.originCurrencyCode &&
                        oldItem.toCurrencyCode == newItem.toCurrencyCode &&
                        oldItem.toAmount == newItem.toAmount &&
                        oldItem.timestamp == newItem.timestamp
            }

            override fun areItemsTheSame(oldItem: Operation, newItem: Operation): Boolean =
                oldItem.id == newItem.id

            override fun getChangePayload(oldItem: Operation, newItem: Operation): List<ChangePayload> {
                val changePayloadList: MutableList<ChangePayload> = mutableListOf()

                if (hasTimestampChanged(oldItem, newItem)){
                    changePayloadList.add(TimestampDataChange)
                }
                if (hasResultChanged(oldItem, newItem)){
                    changePayloadList.add(ResultDataChange)
                }

                return changePayloadList.toList()
            }
        }

        private fun hasTimestampChanged(oldItem: Operation, newItem: Operation): Boolean{
            return oldItem.timestamp != newItem.timestamp

        }

        private fun hasResultChanged(oldItem: Operation, newItem: Operation): Boolean{
            return oldItem.originAmount == newItem.originAmount &&
                    oldItem.originCurrencyCode == newItem.originCurrencyCode &&
                    oldItem.toCurrencyCode == newItem.toCurrencyCode &&
                    oldItem.toAmount == newItem.toAmount
        }
    }

    sealed class ChangePayload
    object TimestampDataChange : ChangePayload()
    object ResultDataChange : ChangePayload()
}

class HistoryViewHolder(
    parent: ViewGroup
): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.history_item, parent, false)
) {

    private val binding: HistoryItemBinding =
        HistoryItemBinding.bind(itemView)

    fun bindTo(operation: Operation) {
        bindDate(operation)
        bindResult(operation)
    }

    fun bindDate(operation: Operation) {
        binding.date.text = operation.timestamp
    }

    fun bindResult(operation: Operation) {
        binding.operation.text = operation.result
    }

}