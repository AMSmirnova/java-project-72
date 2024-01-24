@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@import java.time.format.DateTimeFormatter
@param UrlsPage page

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
                <h1>Сайты</h1>

                <table class="table table-bordered table-hover mt-3">
                    <thead>
                        <tr>
                            <th class="col-1">ID</th>
                            <th>Имя</th>
                            <th class="col-2">Последняя проверка</th>
                            <th class="col-1">Код ответа</th>
                        </tr>

                    </thead>
                    <tbody>
                        @for (var url : page.getUrls())
                            <tr>
                                <th>${url.getId()}</th>
                                <th><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></th>

                                @if (url.getUrlChecks().isEmpty())
                                    <th></th>
                                    <th></th>
                                @else
                                    <th class="col-2">${url.getLastUrlCheck().getCreatedAt().toLocalDateTime().format(formatter)}</th>
                                    <th class="col-1">${url.getLastUrlCheck().getStatusCode()}</th>
                                @endif
                            </tr>
                        @endfor
                    </tbody>
                </table>
            </div>
        </section>
    `
)