package function.designing;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Author: Moriatry
 * Date:   15-12-17
 */
public class AssetUtil {

    public static int totalAssetValues(final List<Asset> assets) {
        return assets.stream()
                .mapToInt(Asset::getValue)
                .sum();
    }

    public static int totalBondValues(final List<Asset> assets) {
        return assets.stream()
                    .filter(item -> item.getType() == Asset.AssetType.BOND)
                    .mapToInt(Asset::getValue)
                    .sum();
    }

    public static int totalStockValues(final List<Asset> assets) {
        return assets.stream()
                    .mapToInt(item->item.getType() == Asset.AssetType.STOCK ? item.getValue() : 0)
                    .sum();
    }

    //perfect version
    public static int totalAssetValues(final List<Asset> assets, final Predicate<Asset> selector) {
        return assets.stream()
                    .filter(selector)
                    .mapToInt(Asset::getValue)
                    .sum();
    }

    public static void main(String[] args) {
        final List<Asset> assets = Arrays.asList(
                new Asset(Asset.AssetType.BOND, 1000),
                new Asset(Asset.AssetType.BOND, 2000),
                new Asset(Asset.AssetType.STOCK, 3000),
                new Asset(Asset.AssetType.STOCK, 4000)
        );

        System.out.println("Total of all assets: " + totalAssetValues(assets));
        System.out.println("Total of all Bond assets: " + totalBondValues(assets));
        System.out.println("Total of all Stock assets: " + totalStockValues(assets));

        System.out.println("==================================================");

        System.out.println("Total of all assets: " + totalAssetValues(assets, asset->true));
        System.out.println("Total of all Bond assets: " + totalAssetValues(assets, asset->asset.getType() == Asset.AssetType.BOND));
        System.out.println("Total of all Stock assets: " + totalAssetValues(assets, asset->asset.getType() == Asset.AssetType.STOCK));
    }
}
