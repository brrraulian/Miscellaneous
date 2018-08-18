<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="Portfolio.Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Ricardo Numa</title>

    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="css/default.css" />
    <link rel="stylesheet" href="css/master.css" />
    <link rel="stylesheet" href="css/page.css" />

</head>
<body>


    <a href="#about" class="button-home">

        <div id="home" class="bg-home">

            <p class="home-text">
                <span class="hello-world feature">Hello World!</span>
                <br />
                <br />
                <br />
                I am Ricardo Numa,
                    <br />
                <span class="feature">Software Developer.</span>
                <br />
                <br />
                <br />
                Welcome to my website.

            </p>

        </div>

    </a>




    <div class="master">

        <table class="master-table-external">
            <tr>
                <td class="master-div-name">
                    <span class="master-feature">Ricardo Numa</span>
                </td>

                <td class="master-div-table">

                    <table class="master-table">
                        <tr>
                            <td>
                                <div class="master-button">
                                    <a class="button" href="#about">ABOUT ME</a>
                                </div>
                            </td>
                            <td>
                                <div class="master-button">
                                    <a class="button" href="#projects">PROJECTS</a>
                                </div>
                            </td>
                            <td>
                                <div class="master-button">
                                    <a class="button" href="#contact">CONTACT</a>
                                </div>
                            </td>
                        </tr>
                    </table>

                </td>

                <td class="master-div"></td>
            </tr>
        </table>

    </div>







    <div class="margin-left-right">

        <div id="about"></div>

        <div class="page bg-about">

            <h2 class="title">ABOUT ME</h2>

            <hr class="hr" />

            <p class="about-text">Meu nome é Ricardo Numa, tenho 28 anos, sou formado em Administração de Empresas e depois de trabalhar alguns anos na área percebi que não era feliz fazendo aquilo.</p>
            <p class="about-text">
                Resolvi mudar de área e depois de pesquisar bastante, acabei me identificando com o mundo de TI, mais especificamente com a parte de desenvolvimento. 
	Engraçado lembrar que quando adolecente, passava horas tentando deixar meu site gratuíto (usando praticamente HTML puro) mais legal para mostrar para meus amigos ou 
	editava mapas de jogos para ficarem do jeito que eu gostaria que fossem.
            </p>
            <p class="about-text">
                Em 2012 comecei a fazer um curso de Lógica de Programação e depois passei a estudar por conta própria através de tutoriais da internet, vídeos, artigos, apostilas e livros. 
	Depois de vários meses de estudo, consegui uma oportunidade de trabalhar como programador freelancer numa empresa de TI. 
	Como o foco dela era em infraestrutura e suporte, no começo só conseguia trabalhos pequenos. Mas com o passar do tempo, percebi como isso me ajudou a ganhar confiança e experiência, 
	necessários para conseguir aumentar cada vez mais o nível de complexidade dos meus sistemas. 
	Durante esse período, todas os sistemas que desenvolvi, tanto web quanto desktop, foram em .NET C#, MS SQL Server, MySQL, HTML, CSS e Javascript.
            </p>
            <p class="about-text">
                Em 2013 juntei coragem o suficiente para começar minha segunda graduação, dessa vez em Ciência da Computação. Graças a experiência adquirida no trabalho, conseguia tirar 
	de letra muitas das matérias da faculdade, enquanto que algumas eram totalmente novas para mim. Através dos trabalhos acadêmicos, pude desenvolver diversos sistemas em Java e 
	em 2014 comecei a estudar Android. Em 2015 surgiu a oportunidade de desenvolver um aplicativo Android para a empresa em que trabalhava, e foi aí que surgiu o Arks Capture.
            </p>
            <p class="about-text">
                Atualmente sou responsável pelos serviços relacionados com o sistema de recuperação de crédito de ICMS, no qual participei de todo desenvolvimento. Quando tenho um tempinho, 
	procuro estudar para me especilizar em mobile e web.
            </p>
            <p class="about-text">
                Espero me formar na faculdade no meio de 2017, para que assim possa dedicar mais tempo a minha família e também ao meu hobbie, 
	que por coinscidência, também é o meu trabalho. ; )
            </p>

        </div>

        <div id="projects"></div>

        <hr />






        <div class="bg-projects">


            <h2 class="title">PROJECTS</h2>

            <hr />

            <table class="table-projects">
                <tr>
                    <td class="project-cell"><a href="Arks.aspx" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/arks.png" alt="Arks Capture" />
                            <div class="middle">
                                <div class="text">
                                    Arks Capture
                                    <br />
                                    <br />
                                    Android app for photos upload from device's gallery or taken by the app itself, to the Arks system (corporate cloud file management platform).
                                </div>
                            </div>
                        </div>
                    </a></td>
                    <td class="project-cell"><a href="Icms.aspx" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/icms.png" alt="ICMS" />
                            <div class="middle">
                                <div class="text">
                                    ICMS
                                    <br />
                                    <br />
                                    Web and desktop system made for text file generation, that is necessary for the ICMS credit request before the Internal Revenue Service.
                                </div>
                            </div>
                        </div>
                    </a></td>
                    <td class="project-cell"><a href="Ceb.aspx" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/ceb.png" alt="Cenoura & Bronze" />
                            <div class="middle">
                                <div class="text">
                                    Cenoura & Bronze
                                    <br />
                                    <br />
                                    Android app that enables the use of camera with filters, frames and sharing for social networks, radio, weather, feeds and sunscreen suggestion.
                                </div>
                            </div>
                        </div>
                    </a></td>
                </tr>
                <tr>
                    <td class="project-cell"><a href="https://play.google.com/store/apps/details?id=br.com.izio.condor" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/condor.png" alt="Clube Condor" />
                            <div class="middle">
                                <div class="text">
                                    Clube Condor
                                    <br />
                                    <br />
                                    Android app for purchase consultation, credits balance and receiving offers.
                                </div>
                            </div>
                        </div>
                    </a></td>
                    <td class="project-cell"><a href="https://play.google.com/store/apps/details?id=com.stoneridge.eld" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/eld.png" alt="Stoneridge EZ-ELD" />
                            <div class="middle">
                                <div class="text">
                                    EZ-ELD Driver App
                                    <br />
                                    <br />
                                    Android app used for truck drivers activities tracking done by an EZ-ELD device via Bluetooth, that creates logs that can be used in road side inspections on USA and Canada. 
                                </div>
                            </div>
                        </div>
                    </a></td>
                    <td class="project-cell"><a href="Izio.aspx" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/pernambucanas.png" alt="Izio" />
                            <div class="middle">
                                <div class="text">
                                    Auto Atendimento Pernambucanas
                                    <br />
                                    <br />
                                    Android app installed at the retailer network self-service terminals for payments.
                                </div>
                            </div>
                        </div>
                    </a></td>
                </tr>
                <tr>
                    <td class="project-cell"><a href="Pernambucanas.aspx" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/pernambucanas2.png" alt="Auto Atendimento Pernambucanas" />
                            <div class="middle">
                                <div class="text">
                                    Proposta Pernambucanas
                                    <br />
                                    <br />
                                    Android tablet app used by the retailer network cards and insurance plans sellers.
                                </div>
                            </div>
                        </div>
                    </a></td>
                    <td class="project-cell"><a href="https://play.google.com/store/apps/details?id=br.com.dahora.petcare" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/petcare.png" alt="Bulário Pet Care" />
                            <div class="middle">
                                <div class="text">
                                    Bulário Pet Care
                                    <br />
                                    <br />
                                    Android app for dogs and cats medicine bulletin, check list and feed calculator.
                                </div>
                            </div>
                        </div>
                    </a></td>
                    <td class="project-cell"><a href="Risque.aspx" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/risque.png" alt="Risqué" />
                            <div class="middle">
                                <div class="text">
                                    Risqué
                                    <br />
                                    <br />
                                    Android app with color enamel simulator.
                                </div>
                            </div>
                        </div>
                    </a></td>
                </tr>
                <tr>
                    <td class="project-cell"><a href="https://play.google.com/store/apps/details?id=br.com.shyre" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/shyre.png" alt="Shyre" />
                            <div class="middle">
                                <div class="text">
                                    Shyre
                                    <br />
                                    <br />
                                    Android app for routing planning and control, calendar and reports to field teams.
                                </div>
                            </div>
                        </div>
                    </a></td>
                    <td class="project-cell"><a href="https://play.google.com/store/apps/details?id=com.trrsecuritas.segurados" target="#">
                        <div class="container">
                            <img class="project-image" src="img/projects/trr.png" alt="TRR" />
                            <div class="middle">
                                <div class="text">
                                    TRR
                                    <br />
                                    <br />
                                    Android app for health plan management, feeds, request and refunds follow-up, accredited network consultation and concierge.
                                </div>
                            </div>
                        </div>
                    </a></td>
                </tr>
            </table>

        </div>


        <hr />






        <div id="contact" class="bg-contact">


            <h2 class="title">CONTACT</h2>

            <hr />

            <table class="contact-table">
                <tr>
                    <td>
                        <a href="https://github.com/brrraulian" target="#">
                            <img src="img/github.png" class="contact-image" /></a>
                    </td>
                    <td class="contact-td">
                        <span class="contact-text">https://github.com/brrraulian</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="https://linkedin.com/in/ricardo-numa-16ab581b/" target="#">
                            <img src="img/linkedin.png" class="contact-image" /></a>
                    </td>
                    <td class="contact-td">
                        <span class="contact-text">https://linkedin.com/in/ricardo-numa-16ab581b/</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img src="img/mail.png" class="contact-image" />
                    </td>
                    <td class="contact-td">
                        <span class="contact-text">koki_numa@hotmail.com</span>
                    </td>
                </tr>
            </table>


        </div>

    </div>

    <div class="footer">
        Made with
        <img src="" />
        in São Paulo-BR
            <br />
        <br />
        © 2018 Ricardo Numa
    </div>





</body>
</html>
