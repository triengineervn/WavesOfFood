import android.os.Parcel
import android.os.Parcelable
import com.example.wavesoffood.models.BaseItem

class CartItem(
    name: String? = null,
    price: String? = null,
    image: String? = null,
    description: String? = null,
    ingredients: String? = null,
    var quantity: Int? = null
) : BaseItem(name, price, image, description, ingredients), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(ingredients)
        parcel.writeValue(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(parcel: Parcel): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(size: Int): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}
