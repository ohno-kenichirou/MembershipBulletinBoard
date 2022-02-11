/*
	処理内容:	カテゴリー一覧用のデータを保持するクラス
			
	作成者:大野賢一朗 作成日:2022/02/10(木)
*/
package bulletinBoard;

public class CategoryListInfo extends CategoryInfo implements Comparable<CategoryListInfo> {

	public CategoryListInfo(int categoryId, String categoryName, String categoryKana) {
		super(categoryId, categoryName, categoryKana);
	}

	@Override
	public int compareTo(CategoryListInfo c) {
		return this.getCategoryKana().compareTo(c.getCategoryKana());
	}

}
