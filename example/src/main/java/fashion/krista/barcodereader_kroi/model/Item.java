package fashion.krista.barcodereader_kroi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item implements Serializable  {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("sklad_id")
    @Expose
    private int skladId;
    @SerializedName("model_series_id")
    @Expose
    private int modelSeriesId;
    @SerializedName("model_size_id")
    @Expose
    private int modelSizeId;
    @SerializedName("model_spendings_id")
    @Expose
    private int modelSpendingsId;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("history_qty")
    @Expose
    private int historyQty;
    @SerializedName("dupl_model_id")
    @Expose
    private int duplModelId;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("contragent_id")
    @Expose
    private int contragentId;
    @SerializedName("pre_order")
    @Expose
    private int preOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkladId() {
        return skladId;
    }

    public void setSkladId(int skladId) {
        this.skladId = skladId;
    }

    public int getModelSeriesId() {
        return modelSeriesId;
    }

    public void setModelSeriesId(int modelSeriesId) {
        this.modelSeriesId = modelSeriesId;
    }

    public int getModelSizeId() {
        return modelSizeId;
    }

    public void setModelSizeId(int modelSizeId) {
        this.modelSizeId = modelSizeId;
    }

    public int getModelSpendingsId() {
        return modelSpendingsId;
    }

    public void setModelSpendingsId(int modelSpendingsId) {
        this.modelSpendingsId = modelSpendingsId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getHistoryQty() {
        return historyQty;
    }

    public void setHistoryQty(int historyQty) {
        this.historyQty = historyQty;
    }

    public int getDuplModelId() {
        return duplModelId;
    }

    public void setDuplModelId(int duplModelId) {
        this.duplModelId = duplModelId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getContragentId() {
        return contragentId;
    }

    public void setContragentId(int contragentId) {
        this.contragentId = contragentId;
    }

    public int getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(int preOrder) {
        this.preOrder = preOrder;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", skladId=" + skladId +
                ", modelSeriesId=" + modelSeriesId +
                ", modelSizeId=" + modelSizeId +
                ", modelSpendingsId=" + modelSpendingsId +
                ", qty=" + qty +
                ", historyQty=" + historyQty +
                ", duplModelId=" + duplModelId +
                ", price=" + price +
                ", contragentId=" + contragentId +
                ", preOrder=" + preOrder +
                '}';
    }
}
