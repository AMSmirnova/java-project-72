@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.layout.page(
    content = @`


            <section>

                <div class="container-fluid">
                    @if(page != null && page.getFlash() != null)
                        <div class="alert alert-${page.getFlashType()}" role="alert">
                            ${page.getFlash()}
                        </div>
                    @endif
                </div>

                <div class="container-lg mt-5">
                    <h1>Сайт: ${page.getUrl().getName()}</h1>

                    <table class="table table-bordered table-hover mt-3">
                        <tbody>
                        <tr>
                            <td>ID</td>
                            <td>${page.getUrl().getId()}</td>
                        </tr>
                        <tr>
                            <td>Имя</td>
                            <td>${page.getUrl().getName()}</td>
                        </tr>
                        <tr>
                            <td>Дата создания</td>
                            <td>${page.getUrl().getCreatedAt().toString()}</td>
                        </tr>
                        </tbody>
                    </table>

                </div>

                <div class="container-lg mt-5">
                    <h1>Проверки</h1>
                    <form method="post" action="${NamedRoutes.checksPath(page.getUrl().getId())}">
                        <button type="submit" class="btn btn-primary">Запустить проверку</button>
                    </form>

                    <table class="table table-bordered table-hover mt-3">
                        <thead>
                            <tr>
                                <th class="col-1">ID</th>
                                <th>Код ответа</th>
                                <th class="col-2">title</th>
                                <th class="col-1">h1</th>
                                <th class="col-1">description</th>
                                <th class="col-1">Дата проверки</th>
                            </tr>

                        </thead>
                        <tbody>
                        @if (page.getUrl().getUrlChecks() != null)
                            @for (var urlCheck : page.getUrl().getUrlChecks())
                                <tr>
                                    <th class="col-1">${urlCheck.getId()}</th>
                                    <th>${urlCheck.getStatusCode()}</th>
                                    <th class="col-2">${urlCheck.getTitle()}</th>
                                    <th class="col-1">${urlCheck.getH1()}</th>
                                    <th class="col-1">${urlCheck.getDescription()}</th>
                                    <th class="col-1">${String.valueOf(urlCheck.getCreatedAt())}</th>
                                </tr>
                            @endfor
                        @endif

                            </tbody>
                    </table>

                </div>

            </section>

    `
)