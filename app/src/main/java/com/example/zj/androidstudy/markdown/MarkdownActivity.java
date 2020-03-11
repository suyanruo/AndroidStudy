package com.example.zj.androidstudy.markdown;

import androidx.appcompat.app.AppCompatActivity;
import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.styles.Github;

import android.os.Bundle;

import com.example.zj.androidstudy.R;

public class MarkdownActivity extends AppCompatActivity {
  private MarkdownView mMarkdownView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_markdown);

    mMarkdownView = findViewById(R.id.markdown_view);
    mMarkdownView.addStyleSheet(new Github());
    mMarkdownView.loadMarkdown("<center>Please read carefully</center>\n" +
        "\n" +
        "1. CashKey provides you convenient online loan service, hopes to meet your funding needs. It is best that you repay on time and accumulate good credit;\n" +
        "\n" +
        "2. Once you are overdue, we will truthfully upload your overdue records to financial authorities such as the RBI, This will affect your borrowing and other financial services in the future;\n" +
        "\n" +
        "3. If you are applying for a loan it implies that you are signing a [\"Loan Agreement\"](https://ck-test.fintopia.tech/webview/agreement?key=CASHKEY_LOAN)  that is compliant with the laws and regulations of India with the lender. Please check it carefully. Click to view the [\"Loan Agreement\"](https://ck-test.fintopia.tech/webview/agreement?key=CASHKEY_LOAN).");
  }
}
