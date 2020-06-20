package ie.app.mycoffeeapp.ui.article;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import ie.app.mycoffeeapp.R;
import ie.app.mycoffeeapp.model.Article;

@SuppressLint("Registered")
public class ArticleActivity extends AppCompatActivity {
    private static final String TAG = "ArticleActivity";
    private ArticleViewModel articleViewModel;
    private TextView articleTitle;
    private HtmlTextView articleContent;
    private ImageView articleImage;
    private String articleId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            articleId = bundle.getString("articleId");
            Log.d(TAG, "savedInstanceState " + articleId);
            if (articleId != null) {
                articleViewModel = new ArticleViewModel(articleId, this);
            } else {
                articleViewModel = new ArticleViewModel("-1", this);
            }
        }
        setContentView(R.layout.activity_article);

        articleTitle = findViewById(R.id.articleTitle);
        articleContent = findViewById(R.id.articleContent);
        articleImage = findViewById(R.id.articleImage);

        final Toolbar toolbar = findViewById(R.id.appbar);
        final TextView title = toolbar.findViewById(R.id.article_title);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
//            actionBar.setHideOnContentScrollEnabled(true);
        }

        articleViewModel.getArticle().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(Article article) {
                articleTitle.setText(article.getName());
                Glide.with(getApplicationContext()).asBitmap().load(article.getImage_link()).into(articleImage);
                articleContent.setHtml(article.getDetail(),new HtmlHttpImageGetter(articleContent));
                title.setText(article.getName());
            }
        });
    }

}

