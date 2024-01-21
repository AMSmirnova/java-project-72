package gg.jte.generated.ondemand;
import hexlet.code.dto.BasePage;
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,3,3,5,5,8,8,9,9,9,9,10,10,10,12,12,36,36,36};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BasePage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n\n    <div class=\"container-fluid\">\n        ");
				if (page != null && page.getFlash() != null) {
					jteOutput.writeContent("\n            <div class=\"alert alert-");
					jteOutput.setContext("div", "class");
					jteOutput.writeUserContent(page.getFlashType());
					jteOutput.setContext("div", null);
					jteOutput.writeContent("\" role=\"alert\">\n                ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(page.getFlash());
					jteOutput.writeContent("\n            </div>\n        ");
				}
				jteOutput.writeContent("\n    </div>\n\n        <div class=\"container-fluid p-5\">\n            <h1>Анализатор страниц</h1>\n            <p class=\"lead\">Бесплатно проверяйте сайты на SEO пригодность</p>\n            <form action=\"/urls\" method=\"post\">\n                <div class=\"row\">\n                    <div class=\"col\">\n                        <div class=\"form-floating\">\n                            <input class=\"form-control\" required name=\"url\" id=\"url-input\" placeholder=\"Ссылка\">\n                            <label for=\"url-input\">Ссылка</label>\n                        </div>\n                    </div>\n\n                    <div class=\"col-auto\">\n                        <button type=\"submit\" class=\"h-100 btn btn-lg btn-primary px-sm-5\">Проверить</button>\n                    </div>\n                </div>\n            </form>\n\n            <p class=\"mt-2 mb-0 text-muted\">Пример: https://www.example.com</p>\n        </div>\n\n    ");
			}
		}, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BasePage page = (BasePage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
