@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlPage page

!{var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");}

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
                            <td>${page.getUrl().getCreatedAt().toLocalDateTime().format(formatter)}</td>
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
                        <thead class="mt-3">
                            <tr>
                                <th class="col-1">ID</th>
                                <th class="col-1">Код ответа</th>
                                <th>title</th>
                                <th>h1</th>
                                <th>description</th>
                                <th class="col-2">Дата проверки</th>
                            </tr>

                        </thead>
                        <tbody>
                        @if (page.getUrl().getUrlChecks() != null)
                            @for (var urlCheck : page.getUrl().getUrlChecks())
                                <tr>
                                    <th>${urlCheck.getId()}</th>
                                    <th>${urlCheck.getStatusCode()}</th>
                                    <th>${urlCheck.getTitle()}</th>
                                    <th>${urlCheck.getH1()}</th>
                                    <th>${urlCheck.getDescription()}</th>
                                    <th>${urlCheck.getCreatedAt().toLocalDateTime().format(formatter)}</th>
                                </tr>
                            @endfor
                        @endif

                        </tbody>
                    </table>
                </div>
            </section>
    `
)