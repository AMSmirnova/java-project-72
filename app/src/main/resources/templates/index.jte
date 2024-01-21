@import hexlet.code.dto.BasePage
@param BasePage page

@template.layout.page(
    content = @`

    <div class="container-fluid">
        @if(page != null && page.getFlash() != null)
            <div class="alert alert-${page.getFlashType()}" role="alert">
                ${page.getFlash()}
            </div>
        @endif
    </div>

        <div class="container-fluid p-5">
            <h1>Анализатор страниц</h1>
            <p class="lead">Бесплатно проверяйте сайты на SEO пригодность</p>
            <form action="/urls" method="post">
                <div class="row">
                    <div class="col">
                        <div class="form-floating">
                            <input class="form-control" required name="url" id="url-input" placeholder="Ссылка">
                            <label for="url-input">Ссылка</label>
                        </div>
                    </div>

                    <div class="col-auto">
                        <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-5">Проверить</button>
                    </div>
                </div>
            </form>

            <p class="mt-2 mb-0 text-muted">Пример: https://www.example.com</p>
        </div>

    `
)